package me.squander.apocalypse.capabilities;

import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class WeaponHandler implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<WeaponHandler> lazyOptional = LazyOptional.of(() -> this);
    private final int maxAmmo = 4;
    private int currentAmmo = 0;

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void takeAmmo(int value){
        if(this.currentAmmo < 0) return;
        this.currentAmmo -= value;
    }

    public void reload(ItemStack ammoToReload){
        int i = this.maxAmmo - currentAmmo;
        if(this.currentAmmo == this.maxAmmo) return;

        if(ammoToReload.getCount() >= this.maxAmmo){
            this.currentAmmo += i;
            ammoToReload.shrink(i);
        }else{
            this.currentAmmo += ammoToReload.getCount();
            ammoToReload.shrink(ammoToReload.getCount());
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityInit.WEAPON_HANDLER_CAPABILITY.orEmpty(cap, lazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentAmmo", this.currentAmmo);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.currentAmmo = nbt.getInt("currentAmmo");
    }
}
