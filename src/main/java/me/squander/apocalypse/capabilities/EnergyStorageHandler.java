package me.squander.apocalypse.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageHandler extends EnergyStorage {
    public EnergyStorageHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Energy", this.energy);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag tag) {
        this.energy = ((CompoundTag) tag).getInt("Energy");
    }
}
