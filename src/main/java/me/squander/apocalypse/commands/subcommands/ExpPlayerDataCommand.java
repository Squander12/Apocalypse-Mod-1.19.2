package me.squander.apocalypse.commands.subcommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.PlayerHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;

public class ExpPlayerDataCommand {

    private static int check(CommandContext<CommandSourceStack> ctx, ServerPlayer player, Type type){
        int i = type.check.applyAsInt(getCap(player));
        ctx.getSource().sendSuccess(Component.translatable("command.apocalypse.xp.check." + type.name, player.getDisplayName(), i), true);
        return i;
    }

    private static int add(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, int amount, Type type){
        for(ServerPlayer player : players){
            if(player == null || !player.getCapability(CapabilityInit.PLAYER_DATA).isPresent()){
                ctx.getSource().sendFailure(Component.translatable("command.apocalypse.xp.add.fail"));
                return 0;
            }

            type.add.accept(getCap(player), amount);
        }

        if(players.size() == 1){
            ctx.getSource().sendSuccess(Component.translatable("command.apocalypse.xp.add." + type.name + ".single", amount, players.iterator().next().getDisplayName()), true);
        }else{
            ctx.getSource().sendSuccess(Component.translatable("command.apocalypse.xp.add." + type.name + ".multi", amount, players.size()), true);
        }

        return players.size();
    }

    private static PlayerHandler getCap(ServerPlayer player){
        return player.getCapability(CapabilityInit.PLAYER_DATA).orElse(null);
    }

    public static ArgumentBuilder<CommandSourceStack,?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("xp")
                .requires(ctx -> ctx.hasPermission(3))
                .then(Commands.literal("add")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .then(Commands.literal("xp").executes(ctx -> add(ctx, EntityArgument.getPlayers(ctx, "targets"), IntegerArgumentType.getInteger(ctx, "amount"), Type.XP)))
                                        .then(Commands.literal("level").executes(ctx -> add(ctx, EntityArgument.getPlayers(ctx, "targets"), IntegerArgumentType.getInteger(ctx, "amount"), Type.LEVEL)))
                                        .then(Commands.literal("skill_points").executes(ctx -> add(ctx, EntityArgument.getPlayers(ctx, "targets"), IntegerArgumentType.getInteger(ctx, "amount"), Type.SKILL_POINTS))))))
                .then(Commands.literal("check")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.literal("xp").executes(ctx -> check(ctx, EntityArgument.getPlayer(ctx, "target"), Type.XP)))
                                .then(Commands.literal("level").executes(ctx -> check(ctx, EntityArgument.getPlayer(ctx, "target"), Type.LEVEL)))
                                .then(Commands.literal("skill_points").executes(ctx -> check(ctx, EntityArgument.getPlayer(ctx, "target"), Type.SKILL_POINTS)))));
    }

    private enum Type{
        XP("xp", PlayerHandler::getCurrentExp, PlayerHandler::addExp),
        LEVEL("level", PlayerHandler::getLevel, PlayerHandler::addLevel),
        SKILL_POINTS("skill_points", PlayerHandler::getSkillPoints, PlayerHandler::addSkillPoints);

        public final ToIntFunction<PlayerHandler> check;
        public final BiConsumer<PlayerHandler, Integer> add;
        public final String name;

        Type(String name, ToIntFunction<PlayerHandler> check, BiConsumer<PlayerHandler, Integer> add){
            this.name = name;
            this.check = check;
            this.add = add;
        }
    }
}
