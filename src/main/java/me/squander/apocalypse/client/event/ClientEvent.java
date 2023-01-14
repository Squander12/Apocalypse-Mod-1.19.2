package me.squander.apocalypse.client.event;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.blockentity.BlockEntityInit;
import me.squander.apocalypse.client.entity.BulletRender;
import me.squander.apocalypse.client.entity.MilitaryChestRender;
import me.squander.apocalypse.client.key.KeyInit;
import me.squander.apocalypse.entity.EntityTypeInit;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.network.packets.server.OpenSkillScreenServer;
import me.squander.apocalypse.network.packets.server.ReloadGunServer;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvent {

    @Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ModInit{
        @SubscribeEvent
        public static void onRenderEntity(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(EntityTypeInit.BULLET.get(), BulletRender::new);
            event.registerBlockEntityRenderer(BlockEntityInit.MILITARY_CHEST.get(), MilitaryChestRender::new);
        }

        @SubscribeEvent
        public static void onRenderOverlay(RegisterGuiOverlaysEvent event){
            //event.registerBelow(VanillaGuiOverlay.FOOD_LEVEL.id(), "ammo_overlay", new AmmoOverlay());
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
    }

    @Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    static class ForgeInit{

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
