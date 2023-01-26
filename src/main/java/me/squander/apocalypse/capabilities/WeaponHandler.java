package me.squander.apocalypse.capabilities;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.item.ItemInit;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
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

    public boolean reload(IItemHandler container){
        if(this.currentAmmo == this.maxAmmo) return false;

        while(this.currentAmmo < this.maxAmmo){
            ItemStack ammoToReload = Helper.findItemInContainer(ItemInit.SHOTGUN_AMMO.get(), container);
            if(ammoToReload.getCount() > 0){
                this.currentAmmo += 1;
                ammoToReload.shrink(1);
            }
        }
        return true;
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
