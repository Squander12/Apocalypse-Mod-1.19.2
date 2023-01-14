package me.squander.apocalypse.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryBlockEntity extends BlockEntity implements MenuProvider {
    protected final ItemStackHandler handler = this.createHandler();
    protected final LazyOptional<IItemHandler> itemOptional = LazyOptional.of(() -> this.handler);

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
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, this.itemOptional);
    }

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
