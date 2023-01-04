package me.squander.apocalypse.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class Packet {
    public Packet(FriendlyByteBuf buffer){}
    protected Packet(){}

    public abstract void encode(FriendlyByteBuf buffer);
    public abstract boolean handle(Supplier<NetworkEvent.Context> ctx);
}
