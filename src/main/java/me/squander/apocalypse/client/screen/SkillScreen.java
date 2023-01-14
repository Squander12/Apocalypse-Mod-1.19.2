package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.client.screen.widget.SkillButton;
import me.squander.apocalypse.helper.ClientHelper;
import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.SkillMenu;
import me.squander.apocalypse.skill.Skill;
import me.squander.apocalypse.skill.SkillsInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class SkillScreen extends AbstractContainerScreen<SkillMenu> {
    private final ResourceLocation TEXTURE = Helper.getRc("textures/gui/skill_menu.png");
    private final Player player = Minecraft.getInstance().player;
    private final int FONT_COLOR = 4210752;
    private int xPos;
    private int yPos;

    public SkillScreen(SkillMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.xPos = (width - imageWidth) / 2;
        this.yPos = (height - imageHeight) / 2;
        this.addRenderableWidget(new SkillButton(this, this.getSkill(SkillsInit.HEALTH_SKILL.get()), this.xPos + 105, this.yPos + 45));
        this.addRenderableWidget(new SkillButton(this, this.getSkill(SkillsInit.STRONG_SKILL.get()), this.xPos + 105, this.yPos + 70));
        this.addRenderableWidget(new SkillButton(this, this.getSkill(SkillsInit.OXYGEN_SKILL.get()), this.xPos + 105, this.yPos + 95, false));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int mouseX, int mouseY) {
        ClientHelper.renderStuff(TEXTURE);
        this.blit(pPoseStack, this.xPos, this.yPos, 0, 0, imageWidth, imageHeight);

        int i = this.getExpBarProgress();
        this.blit(pPoseStack, xPos + 7, yPos + 29, 0, 167, i, 5);

        InventoryScreen.renderEntityInInventory(xPos + 32, yPos + 108, 30, (float)(xPos + 32) - mouseX, (float)(yPos + 105 - 50) - mouseY, this.player);
    }

    private int getExpBarProgress(){
        int exp = this.getHandler().getCurrentExp();
        int maxExp = this.getHandler().getMaxExp();
        int barSize = 162;
        return exp * barSize / maxExp;
    }

    private PlayerHandler getHandler(){
        return this.player.getCapability(CapabilityInit.PLAYER_DATA).orElse(null);
    }

    private Skill getSkill(Skill skill){
        return this.getHandler().getSkill(skill).get();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int level = this.getHandler().getLevel();
        int exp = this.getHandler().getCurrentExp();
        int maxExp = this.getHandler().getMaxExp();
        int skillPoints = this.getHandler().getSkillPoints();

        this.font.draw(pPoseStack, Component.literal("Level: " + level), 8, 17, FONT_COLOR);
        this.font.draw(pPoseStack, Component.literal("Skill Points: " + skillPoints), 8, 122, FONT_COLOR);
        this.font.draw(pPoseStack, Component.literal("Exp: " + exp + "/" + maxExp), 105, 17, FONT_COLOR);
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, FONT_COLOR);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
