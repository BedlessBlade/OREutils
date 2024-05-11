package net.eithan.oreutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.eithan.oreutils.config.ModConfigs;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class BlockListCommands {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("bl").executes(BlockListCommands::showBlockList)
                .then(ClientCommandManager.literal("add").then(ClientCommandManager.argument("username", StringArgumentType.string()).executes(BlockListCommands::addBlockList)))
                .then(ClientCommandManager.literal("remove").then(ClientCommandManager.argument("username", StringArgumentType.string()).suggests(new BlockListRemoveSuggestionProvider()).executes(BlockListCommands::removeBlockList))));
    }

    private static int showBlockList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
        context.getSource().getPlayer().sendMessage(Text.literal("Block list:" + ModConfigs.BLOCK_LIST.toString()));
        return 1;
    }
    private static int addBlockList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
         if(!ModConfigs.addToBlockList(StringArgumentType.getString(context, "username"))) {
             context.getSource().sendFeedback(Text.literal(StringArgumentType.getString(context, "username") + " is already in the block list"));
             return -1;
         }
        context.getSource().sendFeedback(Text.literal("added " + StringArgumentType.getString(context, "username") + " to the block list"));
        return 1;
    }
    private static int removeBlockList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
        if(!ModConfigs.removeFromBlockList(StringArgumentType.getString(context, "username")))
            context.getSource().sendFeedback(Text.literal("failed to remove " + StringArgumentType.getString(context, "username") + " because its not in the block list"));
        else
            context.getSource().sendFeedback(Text.literal("removed " + StringArgumentType.getString(context, "username") + " from the block list"));
        return 1;
    }

}

