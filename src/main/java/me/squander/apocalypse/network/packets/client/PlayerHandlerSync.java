package me.squander.apocalypse.network.packets.client;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerHandlerSync extends Packet {
    private final PlayerHandler handler;

    public static PlayerHandlerSync decode(FriendlyByteBuf buffer) {
        PlayerHandler h = new PlayerHandler();
        h.setLevel(buffer.readVarInt());
        h.setCurrentExp(buffer.readVarInt());
        h.setMaxExp(buffer.readVarInt());
        h.setLastLevel(buffer.readVarInt());
        h.setSkillPoints(buffer.readVarInt());
        return new PlayerHandlerSync(h);
    }

    public PlayerHandlerSync(PlayerHandler handler) {
        this.handler = handler;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.handler.getLevel());
        buffer.writeVarInt(this.handler.getCurrentExp());
        buffer.writeVarInt(this.handler.getMaxExp());
        buffer.writeVarInt(this.handler.getLastLevel());
        buffer.writeVarInt(this.handler.getSkillPoints());
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().player;
                player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> data.copy(this.handler));
            });
        });
        return true;
    }

    public static void register(int index){
        PacketHandler.INSTANCE.messageBuilder(PlayerHandlerSync.class, index, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerHandlerSync::decode)
                .encoder(PlayerHandlerSync::encode)
                .consumerNetworkThread(PlayerHandlerSync::handle).add();
    }
}
