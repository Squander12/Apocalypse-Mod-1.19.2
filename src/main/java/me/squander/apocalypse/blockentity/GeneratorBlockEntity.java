package me.squander.apocalypse.blockentity;

import me.squander.apocalypse.block.GeneratorBlock;
import me.squander.apocalypse.capabilities.EnergyStorageHandler;
import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockEntity extends InventoryBlockEntity {
    private final EnergyStorageHandler energy = new EnergyStorageHandler(4000, 32);
    private final LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);
    private final ContainerData data;
    private int fuelTime;
    private int fuelDuration;

    public GeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.GENERATOR.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> GeneratorBlockEntity.this.fuelTime;
                    case 1 -> GeneratorBlockEntity.this.fuelDuration;
                    case 2 -> GeneratorBlockEntity.this.energy.getEnergyStored();
                    case 3 -> GeneratorBlockEntity.this.energy.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0 -> GeneratorBlockEntity.this.fuelTime = value;
                    case 1 -> GeneratorBlockEntity.this.fuelDuration = value;
                    case 2 -> GeneratorBlockEntity.this.energy.receiveEnergy(value, false);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public void tick(BlockState state){
        boolean flag = this.isWorking();
        boolean flag1 = false;
        boolean flag2 = this.energy.getEnergyStored() >= this.energy.getMaxEnergyStored();

        if(this.isWorking()){
            this.fuelTime--;
        }

        ItemStack fuelSlot = this.getItem(0);
        if((this.isWorking() || !fuelSlot.isEmpty()) && !flag2){
            if(!this.isWorking()){
                this.fuelDuration = this.getFuelDuration(fuelSlot);
                this.fuelTime = this.fuelDuration;
                if(!fuelSlot.isEmpty()){
                    fuelSlot.shrink(1);
                }
            }

            this.energy.receiveEnergy(1, false);
        }

        if(flag != this.isWorking()){
            flag1 = true;
            state = state.setValue(GeneratorBlock.LIT, this.isWorking());
            level.setBlock(this.getBlockPos(), state, 3);
        }

        if(flag1){
            setChanged();
        }
    }

    public boolean isWorking(){
        return this.fuelTime > 0;
    }

    public int getFuelDuration(ItemStack stack){
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ENERGY){
            return energyOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("EnergyHandler", this.energy.serializeNBT());
        tag.putInt("FuelTime", this.fuelTime);
        tag.putInt("FuelDuration", this.fuelDuration);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.energy.deserializeNBT(tag.getCompound("EnergyHandler"));
        this.fuelTime = tag.getInt("FuelTime");
        this.fuelDuration = tag.getInt("FuelDuration");
    }

    @Override
    public Component getDisplayName() {
        return Helper.makeGuiTranslationName("generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new GeneratorMenu(containerId, inventory, this.handler, this.getBlockPos(), this.data);
    }
}
