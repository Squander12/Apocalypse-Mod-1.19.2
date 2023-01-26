package me.squander.apocalypse.menu;

import me.squander.apocalypse.block.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GeneratorMenu extends BaseContainer {
    private final ContainerLevelAccess access;
    private final ContainerData data;

    public GeneratorMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(pContainerId, inventory, new ItemStackHandler(1), BlockPos.ZERO, new SimpleContainerData(4));
    }

    public GeneratorMenu(int pContainerId, Inventory inventory, IItemHandler handler, BlockPos pos, ContainerData data) {
        super(MenuTypeInit.GENERATOR.get(), pContainerId);
        this.access = ContainerLevelAccess.create(inventory.player.level, pos);
        this.data = data;

        this.addSlot(new SlotItemHandler(handler, 0, 80, 48));
        this.addInventory(inventory);
        this.addHotbar(inventory);
        this.addDataSlots(this.data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, BlockInit.GENERATOR.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack toReturn = ItemStack.EMPTY;
        Slot slot = this.getSlot(pIndex);
        if(slot.hasItem()){
            ItemStack slotItem = slot.getItem();
            toReturn = slotItem.copy();
            if(pIndex < 1){
                if(!this.moveItemStackTo(slotItem, 1, this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if(!this.moveItemStackTo(slotItem, 0, 1, false)){
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == toReturn.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotItem);
        }
        return toReturn;
    }

    public int getEnergy(){
        return this.data.get(2);
    }

    public int getMaxEnergy(){
        return this.data.get(3);
    }

    public boolean isWorking(){
        return this.data.get(0) > 0;
    }

    public int getFuelProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.data.get(0) * 13 / i;
    }
}
