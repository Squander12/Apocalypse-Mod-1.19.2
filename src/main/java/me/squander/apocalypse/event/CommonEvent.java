package me.squander.apocalypse.event;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.commands.CommandInit;
import me.squander.apocalypse.entity.Biter;
import me.squander.apocalypse.entity.EntityTypeInit;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvent {

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event){
        if(event.getEntity() instanceof Player player && !player.level.isClientSide()){
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> data.tick(player));
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event){
        CommandInit.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event){
        Entity source = event.getSource().getEntity();
        LivingEntity entity = event.getEntity();
        Level level = entity.level;

        if(!level.isClientSide()){
            if(source instanceof Player player && entity instanceof Zombie){
                player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                    int i = 15 + player.getRandom().nextInt(6);
                    data.addExp(i);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onAttachToPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            event.addCapability(Helper.getRc("player_data"), new PlayerHandler());
        }
    }

    @Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    static class ModEvents{

        @SubscribeEvent
        public static void onCreateAttribute(EntityAttributeCreationEvent event){
            event.put(EntityTypeInit.BITER.get(), Biter.createAttributes().build());
        }
    }
}
