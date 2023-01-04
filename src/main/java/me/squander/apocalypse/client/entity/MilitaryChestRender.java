package me.squander.apocalypse.client.entity;

import me.squander.apocalypse.blockentity.MilitaryChestBlockEntity;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

public class MilitaryChestRender extends ChestRenderer<MilitaryChestBlockEntity> {
    public static final ResourceLocation MILITARY_CHEST = Helper.getRc("chests/military_chest/normal");
    public static final ResourceLocation MILITARY_CHEST_LEFT = Helper.getRc("chests/military_chest/normal_left");
    public static final ResourceLocation MILITARY_CHEST_RIGHT = Helper.getRc("chests/military_chest/normal_right");

    public MilitaryChestRender(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    protected Material getMaterial(MilitaryChestBlockEntity blockEntity, ChestType chestType) {
        return new Material(Sheets.CHEST_SHEET, chooseMaterial(chestType, MILITARY_CHEST, MILITARY_CHEST_LEFT, MILITARY_CHEST_RIGHT));
    }

    private static ResourceLocation chooseMaterial(ChestType pChestType, ResourceLocation pDoubleMaterial, ResourceLocation pLeftMaterial, ResourceLocation pRightMaterial) {
        switch (pChestType) {
            case LEFT:
                return pLeftMaterial;
            case RIGHT:
                return pRightMaterial;
            case SINGLE:
            default:
                return pDoubleMaterial;
        }
    }
}
