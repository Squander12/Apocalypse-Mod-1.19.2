package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import me.squander.apocalypse.client.screen.widget.EnergyWidget;
import me.squander.apocalypse.helper.ClientHelper;
import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.GeneratorMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    public final ResourceLocation TEXTURE = Helper.getRc("textures/gui/generator.png");
    private int xPos;
    private int yPos;

    public GeneratorScreen(GeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.xPos = (width - imageWidth) / 2;
        this.yPos = (height - imageHeight) / 2;

        this.addRenderableWidget(new EnergyWidget(this, Pair.of(this.menu::getEnergy, this.menu::getMaxEnergy),
                ClientHelper.getWidgetsLocation(), this.xPos + 125, this.yPos + 20));
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        ClientHelper.renderStuff(TEXTURE);
        this.blit(poseStack, this.xPos, this.yPos, 0, 0, imageWidth, imageHeight);

        if(this.menu.isWorking()){
            int progress = this.menu.getFuelProgress();
            this.blit(poseStack, this.xPos + 81, this.yPos + 31 + 12 - progress, 176, 12 - progress, 14, progress + 1);
        }
    }

    @Override
    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(poseStack);
        super.render(poseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(poseStack, pMouseX, pMouseY);
    }
}
