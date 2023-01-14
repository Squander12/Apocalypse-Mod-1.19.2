package me.squander.apocalypse.network.packets.server;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.menu.SkillMenu;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.network.packets.client.PlayerHandlerSync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenSkillScreenServer extends Packet {
    public OpenSkillScreenServer(){}
    public OpenSkillScreenServer(FriendlyByteBuf buffer) {}

    @Override
    public void encode(FriendlyByteBuf buffer) {}

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                NetworkHooks.openScreen(player, this.getProvider());
                PacketHandler.sendToClient(new PlayerHandlerSync(data), player);
            });
        });
        return true;
    }

    private MenuProvider getProvider(){
        return new SimpleMenuProvider((id, inv, p) -> new SkillMenu(id, inv), PlayerHandler.SKILL_MENU_NAME);
    }

    public static void register(int index){
        PacketHandler.INSTANCE.messageBuilder(OpenSkillScreenServer.class, index, NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenSkillScreenServer::new)
                .encoder(OpenSkillScreenServer::encode)
                .consumerNetworkThread(OpenSkillScreenServer::handle).add();
    }
}
