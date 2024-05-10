package net.eithan.oreutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.eithan.oreutils.config.ModConfigs;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class BooleanCommands {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("autowb").executes(BooleanCommands::showAutoWB).then(ClientCommandManager.argument("boolean", BoolArgumentType.bool()).executes(BooleanCommands::autoWB)));
        final LiteralCommandNode<FabricClientCommandSource> nameTagCensorNode = dispatcher.register(ClientCommandManager.literal("nametagcensor").executes(BooleanCommands::showNameTagCensor).then(ClientCommandManager.argument("boolean", BoolArgumentType.bool()).executes(BooleanCommands::nameTagCensor)));
        final LiteralCommandNode<FabricClientCommandSource> hideBlockedPlayersNode = dispatcher.register(ClientCommandManager.literal("hideblockedplayers").executes(BooleanCommands::showHideBlockedPlayers).then(ClientCommandManager.argument("boolean", BoolArgumentType.bool()).executes(BooleanCommands::hideBlockedPlayers)));
        final LiteralCommandNode<FabricClientCommandSource> hideBlockedMessagesNode = dispatcher.register(ClientCommandManager.literal("hideblockedmessages").executes(BooleanCommands::showHideBlockedMessages).then(ClientCommandManager.argument("boolean", BoolArgumentType.bool()).executes(BooleanCommands::hideBlockedMessages)));
        final LiteralCommandNode<FabricClientCommandSource> kickOnSightNode = dispatcher.register(ClientCommandManager.literal("kickonsight").executes(BooleanCommands::showKickOnSight).then(ClientCommandManager.argument("boolean", BoolArgumentType.bool()).executes(BooleanCommands::kickOnSight)));
        dispatcher.register(ClientCommandManager.literal("ntc").executes(BooleanCommands::showNameTagCensor).redirect(nameTagCensorNode));
        dispatcher.register(ClientCommandManager.literal("hbp").executes(BooleanCommands::showHideBlockedPlayers).redirect(hideBlockedPlayersNode));
        dispatcher.register(ClientCommandManager.literal("hbm").executes(BooleanCommands::showHideBlockedMessages).redirect(hideBlockedMessagesNode));
        dispatcher.register(ClientCommandManager.literal("kos").executes(BooleanCommands::showKickOnSight).redirect(kickOnSightNode));
    }

    private static int showAutoWB(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        player.sendMessage(Text.of("auto wb feature is: " + ModConfigs.AUTO_WB));
        return 1;
    }

    private static int showNameTagCensor(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        player.sendMessage(Text.of("name tag censoring is: " + ModConfigs.NAME_TAG_CENSOR));
        return 1;
    }

    private static int showHideBlockedPlayers(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        player.sendMessage(Text.of("hide blocked players is: " + ModConfigs.HIDE_BLOCKED_PLAYERS));
        return 1;
    }

    private static int showHideBlockedMessages(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        player.sendMessage(Text.of("hide blocked messages is: " + ModConfigs.HIDE_BLOCKED_MESSAGES));
        return 1;
    }

    private static int showKickOnSight(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        player.sendMessage(Text.of("kick on sight is: " + ModConfigs.KICK_ON_SIGHT));
        return 1;
    }

    private static int autoWB(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        ModConfigs.setAutoWb(BoolArgumentType.getBool(context,"boolean"));
        player.sendMessage(Text.of("set auto wb feature to " + ModConfigs.AUTO_WB));
        return 1;
    }

    private static int nameTagCensor(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        ModConfigs.setNameTagCensor(BoolArgumentType.getBool(context,"boolean"));
        player.sendMessage(Text.of("set name tag censoring to " + ModConfigs.NAME_TAG_CENSOR));
        return 1;
    }

    private static int hideBlockedPlayers(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        ModConfigs.setHideBlockedPlayers(BoolArgumentType.getBool(context,"boolean"));
        player.sendMessage(Text.of("set hide blocked players to " + ModConfigs.HIDE_BLOCKED_PLAYERS));
        return 1;
    }

    private static int hideBlockedMessages(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        ModConfigs.setHideBlockedMessages(BoolArgumentType.getBool(context,"boolean"));
        player.sendMessage(Text.of("set hide blocked messages to " + ModConfigs.HIDE_BLOCKED_MESSAGES));
        return 1;
    }

    private static int kickOnSight(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayerEntity player = context.getSource().getPlayer();
        if(player == null)
            return -1;
        ModConfigs.setKickOnSight(BoolArgumentType.getBool(context,"boolean"));
        player.sendMessage(Text.of("set kick on sight to " + ModConfigs.KICK_ON_SIGHT));
        return 1;
    }
}