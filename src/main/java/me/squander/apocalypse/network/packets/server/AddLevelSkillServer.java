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

public class AddLevelSkillServer extends Packet {
    public static final Component NO_SKILL_POINTS = Component.translatable("add_skill_level_server_packet.no_skill_points").withStyle(ChatFormatting.RED);

        this.levelSkill = levelSkill;
        this.maxLevelSkill = maxLevelSkill;
    public AddLevelSkillServer(ResourceLocation skillResourceLocation) {
        this.skillResourceLocation = skillResourceLocation;
    }

    public AddLevelSkillServer(FriendlyByteBuf buffer) {
        this(buffer.readResourceLocation());
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.skillResourceLocation);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                data.getSkill(this.skillResourceLocation).ifPresent(skill -> {
                    boolean flag = skill.getLevel() != skill.getMaxLevel();
                    int skillPoints = data.getSkillPoints();

                    if(flag){
                        if(skillPoints > 0){
                            data.setSkillPoints(skillPoints - 1);
                            skill.addLevel(1);
                            if(skill.canBeUsed()) {
                                skill.apply(player);
                            }
                        }else{
                            player.displayClientMessage(NO_SKILL_POINTS, false);
                        }
                    }else{
                        player.displayClientMessage(MAX_SKILL_LEVEL, false);
                    }
                });
                PacketHandler.sendToClient(new PlayerHandlerSync(data), player);
            });

        });
        return true;
    }

    public static void register(int id){
        PacketHandler.INSTANCE.messageBuilder(AddLevelSkillServer.class, id, NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddLevelSkillServer::new)
                .encoder(AddLevelSkillServer::encode)
                .consumerNetworkThread(AddLevelSkillServer::handle).add();
    }
}
