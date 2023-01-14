package me.squander.apocalypse.event;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvent {

    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickBlock event){
        Level level = event.getLevel();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        Block block = level.getBlockState(event.getPos()).getBlock();

        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                if(block == Blocks.OBSIDIAN){
                    data.reset(player);
                }

                if(block == Blocks.DIAMOND_BLOCK){
                    data.setLevel(data.getLevel() + 1);
                }

                if(block == Blocks.GOLD_BLOCK){
                    data.addExp(100);
                }

                if(block == Blocks.IRON_BLOCK){
                    data.getSkills().forEach(x -> {
                        ApocalypseMod.LOGGER.info(x.getName() + " level " + x.getLevel());
                    });
                }
            });
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event){
        if(event.getEntity() instanceof Player player && !player.level.isClientSide()){
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(PlayerHandler::tick);
        }
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
}
