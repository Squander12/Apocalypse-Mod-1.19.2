package me.squander.apocalypse.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Helper {

    public static ResourceLocation getRc(String loc){
        return new ResourceLocation(ApocalypseMod.MODID, loc);
    }

    public static Component makeGuiTranslationName(String loc){
        return Component.translatable("container.apocalypse." + loc);
    }

    public static String getArmorTextureLocation(ArmorMaterial armorMaterial, boolean legs){
        String s = getRc("textures/armor/" + armorMaterial.getName() + ".png").toString();
        String s1 = getRc("textures/armor/" + armorMaterial.getName() + "_legs.png").toString();
        return legs ? s : s1;
    }

    public static ItemStack findItemInPlayerInventory(Item item, Player player){
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack f = inventory.getItem(i);
            if(item == f.getItem()){
                return f;
            }
        }
        return ItemStack.EMPTY;
    }

    //From Vanilla Gui class
    public static void renderTextureOverlay(ResourceLocation pTextureLocation, float pAlpha, int screenWidth, int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, pAlpha);
        RenderSystem.setShaderTexture(0, pTextureLocation);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
