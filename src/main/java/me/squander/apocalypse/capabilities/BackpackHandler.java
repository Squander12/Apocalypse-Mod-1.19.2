package me.squander.apocalypse.capabilities;

import me.squander.apocalypse.item.BackpackItem;
import me.squander.apocalypse.misc.BackpackType;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class BackpackHandler extends ItemStackHandler implements ICapabilityProvider {
    private final LazyOptional<BackpackHandler> lazyOptional = LazyOptional.of(() -> this);
    private final BackpackType type;

    public BackpackHandler(BackpackType type) {
        super(type.size);
        this.type = type;
    }

    public BackpackType getType(){
        return this.type;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return !(stack.getItem() instanceof BackpackItem);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityInit.BACKPACK_HANDLER.orEmpty(cap, this.lazyOptional);
    }
}
