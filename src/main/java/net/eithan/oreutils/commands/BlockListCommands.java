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
                .then(ClientCommandManager.literal("add").then(ClientCommandManager.argument("blockValue1", StringArgumentType.string()).executes(BlockListCommands::addBlockList)))
                .then(ClientCommandManager.literal("remove").then(ClientCommandManager.argument("blockValue2", StringArgumentType.string()).executes(BlockListCommands::removeBlockList))));
    }

    private static int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().getPlayer() == null)
            return -1;
        context.getSource().getPlayer().networkHandler.sendChatMessage("/build");
        return 1;
    }

    private static int showBlockList(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().getPlayer() == null)
            return -1;
        context.getSource().getPlayer().sendMessage(Text.literal(ModConfigs.BLOCK_LIST.toString()));
        return 1;
    }
    private static int addBlockList(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().getPlayer() == null)
            return -1;
        ModConfigs.addToBlockList(StringArgumentType.getString(context, "blockValue1"));
        context.getSource().sendFeedback(Text.literal("added " + StringArgumentType.getString(context, "blockValue1") + " to the block list"));
        return 1;
    }
    private static int removeBlockList(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().getPlayer() == null)
            return -1;
        if(!ModConfigs.removeFromBlockList(StringArgumentType.getString(context, "blockValue2")))
            context.getSource().sendFeedback(Text.literal("failed to remove " + StringArgumentType.getString(context, "blockValue2") + " because its not in the block list"));
        else
            context.getSource().sendFeedback(Text.literal("removed " + StringArgumentType.getString(context, "blockValue2") + " from the block list"));
        return 1;
    }
}

