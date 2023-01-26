package me.squander.apocalypse.network;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.network.packets.client.PlayerHandlerSync;
import me.squander.apocalypse.network.packets.server.AddLevelSkillServer;
import me.squander.apocalypse.network.packets.server.OpenSkillScreenServer;
import me.squander.apocalypse.network.packets.server.ReloadGunServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Helper.getRc("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(){
        int id = 0;
        ReloadGunServer.register(id++);
        OpenSkillScreenServer.register(id++);
        PlayerHandlerSync.register(id++);
        AddLevelSkillServer.register(id++);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
