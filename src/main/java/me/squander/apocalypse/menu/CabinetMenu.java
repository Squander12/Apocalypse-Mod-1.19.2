package me.squander.apocalypse.menu;

import me.squander.apocalypse.block.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CabinetMenu extends BaseContainer{
    private final ContainerLevelAccess access;

    public CabinetMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(pContainerId, inventory, new ItemStackHandler(9), BlockPos.ZERO);
    }

    public CabinetMenu(int pContainerId, Inventory inventory, IItemHandler handler, BlockPos pos) {
        super(MenuTypeInit.CABINET.get(), pContainerId);
        this.access = ContainerLevelAccess.create(inventory.player.level, pos);

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 3; ++x) {
                this.addSlot(new SlotItemHandler(handler, x + y * 3, 62 + x * 18, 17 + y * 18));
            }
        }

        this.addInventory(inventory);
        this.addHotbar(inventory);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockInit.CABINET.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            returnStack = slotItem.copy();
            if (pIndex < 9) {
                if (!this.moveItemStackTo(slotItem, 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, 0, 9, false)) {
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
