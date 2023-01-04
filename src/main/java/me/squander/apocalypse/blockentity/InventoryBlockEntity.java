package me.squander.apocalypse.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public abstract class InventoryBlockEntity extends BlockEntity {
    protected final ItemStackHandler handler = this.createHandler();

    public InventoryBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    protected ItemStack getItem(int slot){
        return this.handler.getStackInSlot(slot);
    }

    protected void setItem(int slot, ItemStack stack){
        this.handler.setStackInSlot(slot, stack);
    }

    public SimpleContainer container(){
        SimpleContainer container = new SimpleContainer(this.handler.getSlots());
        for (int i = 0; i < this.handler.getSlots(); i++) {
            container.setItem(i, this.getItem(i));
        }
        return container;
    }

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(this.getContainerSize()){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    public abstract int getContainerSize();

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Items", this.handler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.handler.deserializeNBT(tag.getCompound("Items"));
    }
}
