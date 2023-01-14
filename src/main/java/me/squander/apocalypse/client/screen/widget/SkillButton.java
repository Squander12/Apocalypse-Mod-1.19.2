package me.squander.apocalypse.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.network.packets.server.AddSkillLevelServer;
import me.squander.apocalypse.skill.Skill;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class SkillButton extends AbstractButton {
    private final Screen screen;
    private final Skill skill;

    public SkillButton(Screen screen, Skill skill, int x, int y){
        this(screen, skill, x, y, true);
    }

    public SkillButton(Screen screen, Skill skill, int pX, int pY, boolean active) {
        super(pX, pY, 16, 16, Component.literal("+"));
        this.screen = screen;
        this.skill = skill;
        this.active = active;
    }

    @Override
    public void onPress() {
        PacketHandler.sendToServer(new AddSkillLevelServer(this.skill.getName(), this.skill.getLevel(), this.skill.getMaxLevel()));
    }

    @Override //From ExtendedButton
    public void renderButton(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft mc = Minecraft.getInstance();
        int k = this.getYImage(this.isHovered);
        ScreenUtils.blitWithBorder(poseStack, WIDGETS_LOCATION, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
        this.renderBg(poseStack, mc, pMouseX, pMouseY);

        Component buttonText = this.getMessage();
        int strWidth = mc.font.width(buttonText);
        int ellipsisWidth = mc.font.width("...");

        if (strWidth > width - 6 && strWidth > ellipsisWidth)
            buttonText = Component.literal(mc.font.substrByWidth(buttonText, width - 6 - ellipsisWidth).getString() + "...");

        drawCenteredString(poseStack, mc.font, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor());

        if(this.isHoveredOrFocused()){
            this.renderToolTip(poseStack, pMouseX, pMouseY);
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int pMouseX, int pMouseY) {
        List<Component> lore = new ArrayList<>();

        if(this.isActive()){
            lore.add(this.skill.getTranslatableName());
            this.skill.getToolTip(lore);
            lore.add(Component.literal("Level: " + this.skill.getLevel() + "/" + this.skill.getMaxLevel()));
        }else{
            lore.add(Component.literal("INACTIVE").withStyle(ChatFormatting.DARK_RED));
        }

        this.screen.renderComponentTooltip(poseStack, lore, pMouseX, pMouseY);
    }
}
