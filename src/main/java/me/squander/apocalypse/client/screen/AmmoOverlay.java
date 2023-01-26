package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.item.WeaponItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmmoOverlay implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack stack = player.getMainHandItem();

        if(!stack.isEmpty() && stack.getItem() instanceof WeaponItem){
            stack.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).ifPresent(data -> {
                Component c = Component.translatable("ammo_overlay.current_ammo", data.getCurrentAmmo()).withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY);
                int x = (screenWidth - mc.font.width(c)) / 2;

                mc.font.draw(poseStack, c, x, screenHeight - 50, 0);
            });
        }
    }
}
