package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.helper.ClientHelper;
import me.squander.apocalypse.menu.CabinetMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CabinetScreen extends AbstractContainerScreen<CabinetMenu> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");

    public CabinetScreen(CabinetMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        ClientHelper.renderStuff(TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
