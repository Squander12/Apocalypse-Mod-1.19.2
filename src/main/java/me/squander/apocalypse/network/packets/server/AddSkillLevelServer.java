package me.squander.apocalypse.network.packets.server;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.network.packets.client.PlayerHandlerSync;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSkillLevelServer extends Packet {
    private final ResourceLocation loc;
    private final int levelSkill;
    private final int maxLevelSkill;

    public AddSkillLevelServer(ResourceLocation loc, int levelSkill, int maxLevelSkill) {
        this.loc = loc;
        this.levelSkill = levelSkill;
        this.maxLevelSkill = maxLevelSkill;
    }

    public AddSkillLevelServer(FriendlyByteBuf buffer) {
        this(buffer.readResourceLocation(), buffer.readVarInt(), buffer.readVarInt());
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.loc);
        buffer.writeVarInt(this.levelSkill);
        buffer.writeVarInt(this.maxLevelSkill);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                data.getSkill(loc).ifPresent(x -> {
                    x.setLevel(this.levelSkill);
                    x.setMaxLevel(this.maxLevelSkill);

                    int i = data.getSkillPoints();
                    if(i > 0){
                        data.setSkillPoints(i - 1);
                        x.addLevel(1);
                        if(x.canBeUsed()){
                            x.apply(player);
                        }
                    }else{
                        player.displayClientMessage(Component.literal("You don't have enough skill points!").withStyle(ChatFormatting.RED), false);
                    }
                });
                PacketHandler.sendToClient(new PlayerHandlerSync(data), player);
            });

        });
        return true;
    }

    public static void register(int id){
        PacketHandler.INSTANCE.messageBuilder(AddSkillLevelServer.class, id, NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddSkillLevelServer::new)
                .encoder(AddSkillLevelServer::encode)
                .consumerNetworkThread(AddSkillLevelServer::handle).add();
    }
}
