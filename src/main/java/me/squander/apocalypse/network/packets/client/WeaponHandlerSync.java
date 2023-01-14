package me.squander.apocalypse.network.packets.client;

import me.squander.apocalypse.capabilities.WeaponHandler;
import me.squander.apocalypse.network.Packet;
import me.squander.apocalypse.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

//Nie wiadomo czy potrzebne
public class WeaponHandlerSync extends Packet {
    private ItemStack stack;

    public WeaponHandlerSync(ItemStack stack) {
        this.stack = stack;
    }

    public WeaponHandlerSync(FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {

        });
        return true;
    }

    public static void register(int index){
        PacketHandler.INSTANCE.messageBuilder(WeaponHandlerSync.class, index, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WeaponHandlerSync::new)
                .encoder(WeaponHandlerSync::encode)
                .consumerNetworkThread(WeaponHandlerSync::handle).add();
    }
}
