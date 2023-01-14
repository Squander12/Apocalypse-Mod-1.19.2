package me.squander.apocalypse.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import me.squander.apocalypse.helper.ClientHelper;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.IntSupplier;

public class EnergyWidget extends AbstractWidget {
    private final Screen screen;
    private final Pair<IntSupplier, IntSupplier> energy;
    private final ResourceLocation texture;

    public EnergyWidget(Screen screen, Pair<IntSupplier, IntSupplier> energy, ResourceLocation texture, int pX, int pY) {
        super(pX, pY, 18, 49, Component.empty());
        this.screen = screen;
        this.energy = energy;
        this.texture = texture;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        ClientHelper.renderStuff(this.texture);

        this.blit(poseStack, this.x, this.y, 0, 0, 18, 49);

        int energyHeight = this.getEnergyHeight();
        this.blit(poseStack, this.x + 4, this.y + 4 + 41 - energyHeight, 19, 45 - energyHeight, 10, energyHeight);

        this.renderToolTip(poseStack, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int pMouseX, int pMouseY) {
        if(!isMouseOver(pMouseX, pMouseY)) return;

        Component text = Component.literal(this.energy.getFirst().getAsInt() + "/" + this.energy.getSecond().getAsInt() + " FE");
        this.screen.renderTooltip(poseStack, text, pMouseX, pMouseY);
    }

    public int getEnergyHeight(){
        int i = this.energy.getSecond().getAsInt();
        if(i == 0){
            i = 200;
        }
        return this.energy.getFirst().getAsInt() * 41 / i;
    }
}
