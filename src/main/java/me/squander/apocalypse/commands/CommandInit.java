package me.squander.apocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.commands.subcommands.ResetPlayerDataCommand;
import me.squander.apocalypse.commands.subcommands.ExpPlayerDataCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandInit {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal(ApocalypseMod.MODID)
                .then(ResetPlayerDataCommand.register(dispatcher))
                .then(ExpPlayerDataCommand.register(dispatcher)));
    }
}
