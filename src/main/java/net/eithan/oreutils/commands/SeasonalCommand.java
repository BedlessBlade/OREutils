package net.eithan.oreutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class SeasonalCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("seasonal").executes(SeasonalCommand::run));
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().getPlayer() == null)
            return -1;
        context.getSource().getPlayer().networkHandler.sendChatMessage("/seasonal");
        return 1;
    }
}
