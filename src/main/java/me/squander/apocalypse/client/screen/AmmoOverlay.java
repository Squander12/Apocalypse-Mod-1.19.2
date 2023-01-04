package me.squander.apocalypse.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.item.WeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmmoOverlay implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getMainHandItem();

        if(!stack.isEmpty() && stack.getItem() instanceof WeaponItem){
            stack.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).ifPresent(data -> {
                ApocalypseMod.LOGGER.debug("AMMO ON CLIENT: " + data.getCurrentAmmo());
            });
        }
    }
}
