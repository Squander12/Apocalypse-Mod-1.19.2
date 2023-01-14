package me.squander.apocalypse.blockentity;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.CabinetMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CabinetBlockEntity extends InventoryBlockEntity {
    public CabinetBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.CABINET.get(), pPos, pBlockState);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public Component getDisplayName() {
        return Helper.makeGuiTranslationName("cabinet");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CabinetMenu(containerId, inventory, this.handler, this.getBlockPos());
    }
}
