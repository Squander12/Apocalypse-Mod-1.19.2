package me.squander.apocalypse.network.packets.client;

import me.squander.apocalypse.capabilities.PlayerHandler;
import me.squander.apocalypse.helper.ClientHelper;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.skill.Skill;
import me.squander.apocalypse.skill.SkillsInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerHandlerSync extends Packet {
    private final PlayerHandler handler;

    public PlayerHandlerSync(PlayerHandler handler) {
        this.handler = handler;
    }

    public static PlayerHandlerSync decode(FriendlyByteBuf buffer) {
        PlayerHandler h = new PlayerHandler();
        h.setLevel(buffer.readVarInt());
        h.setCurrentExp(buffer.readVarInt());
        h.setSkillPoints(buffer.readVarInt());
        h.setSkills(buffer.readList(PlayerHandlerSync::readSkill));
        return new PlayerHandlerSync(h);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.handler.getLevel());
        buffer.writeVarInt(this.handler.getCurrentExp());
        buffer.writeVarInt(this.handler.getSkillPoints());
        buffer.writeCollection(this.handler.getSkills(), this::writeSkill);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientHelper.playerHandlerSync(this.handler);
        });
        return true;
    }

    private void writeSkill(FriendlyByteBuf buffer, Skill skill){
        buffer.writeResourceLocation(skill.getResourceLocation());
        buffer.writeVarInt(skill.getLevel());
    }

    private static Skill readSkill(FriendlyByteBuf buffer){
        Skill skill = SkillsInit.SKILLS_REGISTRY.get().getValue(buffer.readResourceLocation());
        skill.setLevel(buffer.readVarInt());
        return skill;
    }

    public static void register(int index){
        PacketHandler.INSTANCE.messageBuilder(PlayerHandlerSync.class, index, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerHandlerSync::decode)
                .encoder(PlayerHandlerSync::encode)
                .consumerNetworkThread(PlayerHandlerSync::handle).add();
    }
}
