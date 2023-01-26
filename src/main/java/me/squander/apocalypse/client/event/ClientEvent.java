package me.squander.apocalypse.client.event;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.blockentity.BlockEntityInit;
import me.squander.apocalypse.client.model.ModelLayersInit;
import me.squander.apocalypse.client.renderer.entity.BiterRender;
import me.squander.apocalypse.client.renderer.entity.BulletRender;
import me.squander.apocalypse.client.renderer.block.MilitaryChestRender;
import me.squander.apocalypse.client.key.KeyInit;
import me.squander.apocalypse.client.screen.AmmoOverlay;
import me.squander.apocalypse.entity.EntityTypeInit;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.network.packets.server.OpenSkillScreenServer;
import me.squander.apocalypse.network.packets.server.ReloadGunServer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRenderEntity(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityTypeInit.BULLET.get(), BulletRender::new);
        event.registerEntityRenderer(EntityTypeInit.BITER.get(), BiterRender::new);
        event.registerBlockEntityRenderer(BlockEntityInit.MILITARY_CHEST.get(), MilitaryChestRender::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModelLayersInit.BITER, () -> ModelLayersInit.Layer.NORMAL);
        event.registerLayerDefinition(ModelLayersInit.BITER_INNER, () -> ModelLayersInit.Layer.ARMOR_INNER);
        event.registerLayerDefinition(ModelLayersInit.BITER_OUTER, () -> ModelLayersInit.Layer.ARMOR_OUTER);
    }

    @SubscribeEvent
    public static void onRenderOverlay(RegisterGuiOverlaysEvent event){
        event.registerBelow(VanillaGuiOverlay.FOOD_LEVEL.id(), "ammo_overlay", new AmmoOverlay());
    }

    @SubscribeEvent
    public static void onRegisterKey(RegisterKeyMappingsEvent event){
        event.register(KeyInit.RELOAD_KEY.get());
        event.register(KeyInit.SKILL_SCREEN_KEY.get());
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event){
        if(!event.getAtlas().location().equals(Sheets.CHEST_SHEET)){
            return;
        }

        event.addSprite(MilitaryChestRender.MILITARY_CHEST);
        event.addSprite(MilitaryChestRender.MILITARY_CHEST_LEFT);
        event.addSprite(MilitaryChestRender.MILITARY_CHEST_RIGHT);
    }

    @Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    static class ForgeEvents{

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event){
            if(event.phase == TickEvent.Phase.END){
                while(KeyInit.RELOAD_KEY.get().consumeClick()){
                    PacketHandler.sendToServer(new ReloadGunServer());
                }

                while(KeyInit.SKILL_SCREEN_KEY.get().consumeClick()){
                    PacketHandler.sendToServer(new OpenSkillScreenServer());
                }
            }
        }
    }
}
