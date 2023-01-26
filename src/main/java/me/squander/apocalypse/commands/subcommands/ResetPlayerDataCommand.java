package me.squander.apocalypse.commands.subcommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.squander.apocalypse.capabilities.CapabilityInit;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ResetPlayerDataCommand {

    private static int reset(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players){
        for(ServerPlayer player : players){
            if(player == null || !player.getCapability(CapabilityInit.PLAYER_DATA).isPresent()){
                ctx.getSource().sendFailure(Component.translatable("command.apocalypse.reset.fail"));
                return 0;
            }

            player.getCapability(CapabilityInit.PLAYER_DATA).ifPresent(data -> {
                data.reset(player);
            });
        }

        if(players.size() == 1){
            ctx.getSource().sendSuccess(Component.translatable("command.apocalypse.reset.single", players.iterator().next().getDisplayName()), true);
        }else{
            ctx.getSource().sendSuccess(Component.translatable("command.apocalypse.reset.multi", players.size()), true);
        }

        return players.size();
    }

    public static ArgumentBuilder<CommandSourceStack,?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("reset")
                .requires(ctx -> ctx.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> reset(ctx, EntityArgument.getPlayers(ctx, "targets"))));
    }
}
