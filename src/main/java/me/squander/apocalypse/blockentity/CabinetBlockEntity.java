package me.squander.apocalypse.blockentity;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.CabinetMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class CabinetBlockEntity extends LootTableBlockEntity {
    public CabinetBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.CABINET.get(), pPos, pBlockState);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    protected Component getDefaultName() {
        return Helper.makeGuiTranslationName("cabinet");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new CabinetMenu(pContainerId, pInventory, new InvWrapper(this), this.getBlockPos());
    }
}
