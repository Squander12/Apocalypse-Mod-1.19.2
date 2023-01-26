package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.client.key.KeyInit;
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

import java.util.ArrayList;
import java.util.List;

public class SkillScreen extends AbstractContainerScreen<SkillMenu> {
    private final ResourceLocation TEXTURE = Helper.getRc("textures/gui/skill_menu.png");
    private final List<SkillButton> buttons = new ArrayList<>();
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

        addSkillButton(new SkillButton(this, this.getSkill(SkillsInit.HEALTH_SKILL.get()), this.xPos + 32, this.yPos + 144));
        addSkillButton(new SkillButton(this, this.getSkill(SkillsInit.STRONG_SKILL.get()), this.xPos + 64, this.yPos + 144));
        addSkillButton(new SkillButton(this, this.getSkill(SkillsInit.OXYGEN_SKILL.get()), this.xPos + 96, this.yPos + 144, false));
        addSkillButton(new SkillButton(this, this.getSkill(SkillsInit.EXPERIENCE_SKILL.get()), this.xPos + 128, this.yPos + 144));
    }

    private void addSkillButton(SkillButton btn){
        this.addRenderableWidget(btn);
        this.buttons.add(btn);
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
        int maxExp = this.getHandler().getExpRequiredToNextLevel();
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
        for(SkillButton btn : this.buttons){
            if(!btn.isHoveredOrFocused()) continue;
            btn.renderToolTip(pPoseStack, pMouseX - this.xPos, pMouseY - this.yPos);
            break;
        }

        int level = this.getHandler().getLevel();
        int exp = this.getHandler().getCurrentExp();
        int maxExp = this.getHandler().getExpRequiredToNextLevel();
        int skillPoints = this.getHandler().getSkillPoints();

        int i = this.font.width("" + exp);

        this.font.draw(pPoseStack, Component.literal("Level: " + level), 8, 17, FONT_COLOR);
        this.font.draw(pPoseStack, Component.literal("Exp: " + exp + "/" + maxExp), 115 - i , 17, FONT_COLOR);
        this.font.draw(pPoseStack, Component.literal("Skill Points: " + skillPoints), 65, 42, FONT_COLOR);
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, FONT_COLOR);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(KeyInit.SKILL_SCREEN_KEY.get().matches(pKeyCode, pScanCode)){
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        }else{
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }
}
