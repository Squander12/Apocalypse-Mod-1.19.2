package me.squander.apocalypse.network.packets.server;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.item.WeaponItem;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.sound.SoundEventInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadGunServer extends Packet {
    public ReloadGunServer() {}
    public ReloadGunServer(FriendlyByteBuf buffer) {}

    @Override
    public void encode(FriendlyByteBuf buffer) {}

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ServerLevel level = ctx.getSender().getLevel();
            ItemStack gun = player.getMainHandItem();

            if(gun.getItem() instanceof WeaponItem && !gun.isEmpty()){
                gun.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).ifPresent(data -> {
                    if(data.reload(new InvWrapper(player.getInventory())))
                        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEventInit.SHOTGUN_RELOAD.get(), SoundSource.PLAYERS, 1.0F, 1.0F );
                });
            }
        });
        return true;
    }

    public static void register(int index) {
        PacketHandler.INSTANCE.messageBuilder(ReloadGunServer.class, index, NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReloadGunServer::new)
                .encoder(ReloadGunServer::encode)
                .consumerNetworkThread(ReloadGunServer::handle).add();
    }
}
