package me.squander.apocalypse.blockentity;

import me.squander.apocalypse.helper.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MilitaryChestBlockEntity extends ChestBlockEntity {
    public static final Component CONTAINER_NAME = Helper.makeGuiTranslationName("military_chest");

    public MilitaryChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.MILITARY_CHEST.get(), pPos, pBlockState);
    }

    @Override
    protected Component getDefaultName() {
        return CONTAINER_NAME;
    }
}
