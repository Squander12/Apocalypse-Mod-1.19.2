package me.squander.apocalypse.menu;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.misc.BackpackType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackMenu extends BaseContainer {
    private final ItemStack backpack;
    private BackpackType type;

    public BackpackMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(pContainerId, inventory, buffer.readItem());
    }

    public BackpackMenu(int pContainerId, Inventory inventory, ItemStack backpack) {
        super(MenuTypeInit.BACKPACK.get(), pContainerId);
        this.backpack = backpack;

        this.backpack.getCapability(CapabilityInit.BACKPACK_HANDLER).ifPresent(h -> {
            this.type = h.getType();

            for(int y = 0; y < h.getType().row; y++) {
                for (int x = 0; x < h.getType().column; x++) {
                    this.addSlot(new SlotItemHandler(h, x + y * h.getType().column, h.getType().xStart + x * 18, h.getType().yStart + y * 18));
                }
            }

            this.addInventory(inventory);
            this.addHotbar(inventory);
        });
    }

    public ResourceLocation getTextureLocation(){
        return Helper.getRc("textures/gui/" + type.name + ".png");
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem() == this.backpack || player.getOffhandItem() == this.backpack;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            returnStack = slotItem.copy();
            if (pIndex < this.type.size) {
                if (!this.moveItemStackTo(slotItem, this.type.size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, 0, this.type.size, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotItem);
        }

        return returnStack;
    }
}
