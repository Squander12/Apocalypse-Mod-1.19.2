package me.squander.apocalypse.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.block.BlockInit;
import me.squander.apocalypse.blockentity.MilitaryChestBlockEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ChestItemRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {
    private final BlockEntityRenderDispatcher be;

    public ChestItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.be = pBlockEntityRenderDispatcher;
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType transformType, PoseStack stack, MultiBufferSource buf, int pPackedLight, int pPackedOverlay) {
        this.be.renderItem(new MilitaryChestBlockEntity(BlockPos.ZERO, BlockInit.MILITARY_CHEST.get().defaultBlockState()), stack, buf, pPackedLight, pPackedOverlay);
    }
}
