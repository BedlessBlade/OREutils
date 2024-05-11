package net.eithan.oreutils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.eithan.oreutils.config.ModConfigs;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import java.util.Arrays;

public class PlotCoordsCommands {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("plotcoords").executes(PlotCoordsCommands::showPlotCoordsList)
                        .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("x1", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("z1", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("x2", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("z2", IntegerArgumentType.integer()).executes(PlotCoordsCommands::addPlotCoordsList))))))
                .then(ClientCommandManager.literal("remove").then(ClientCommandManager.argument("plot coordinates", StringArgumentType.string()).suggests(new PlotCoordsRemoveSuggestionProvider()).executes(PlotCoordsCommands::removePlotCoordsList))));
    }

    private static int showPlotCoordsList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
        context.getSource().getPlayer().sendMessage(Text.literal("Plots defined: " + ModConfigs.arrayListToString(ModConfigs.PLOT_COORDINATES)));
        return 1;
    }

    private static int addPlotCoordsList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
        int[] newPlot = new int[]{IntegerArgumentType.getInteger(context,"x1"), IntegerArgumentType.getInteger(context,"z1"), IntegerArgumentType.getInteger(context,"x2"), IntegerArgumentType.getInteger(context,"z2")};
        if(!ModConfigs.addToPlotCoordinates(newPlot)) {
            context.getSource().sendFeedback(Text.literal( "you've reached the limit of 5 plots"));
            return -1;
        }
        context.getSource().sendFeedback(Text.literal("added " + Arrays.toString(newPlot) + " to the plot coordinates list"));
        return 1;
    }

    private static int removePlotCoordsList(CommandContext<FabricClientCommandSource> context) {
        if(context.getSource().getPlayer() == null)
            return -1;
        String plot = StringArgumentType.getString(context, "plot coordinates");
        if(plot.isBlank() || !plot.startsWith("[") || !plot.endsWith("]")) {
            context.getSource().sendFeedback(Text.literal("invalid input, please enter an array with 4 numbers, example: [1,2,3,4]"));
            return -1;
        }
        String shortenedPlot = plot.substring(1, plot.length() - 1);
        String[] splitCoords = shortenedPlot.replace(" ", "").split(",");
        if(splitCoords.length != 4) {
            context.getSource().sendFeedback(Text.literal("invalid coordinate array, valid input needs to have 4 numbers"));
            return -1;
        }
        int[] finalCoords;
        try{
            finalCoords = Arrays.stream(splitCoords).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            context.getSource().sendFeedback(Text.literal("invalid coordinate array, array must hold only numbers"));
            return -1;
        }
        if(!ModConfigs.removeFromPlotCoordinates(finalCoords)) {
            context.getSource().sendFeedback(Text.literal("failed to remove " + shortenedPlot + " because its not in the plot coordinates list"));
            return -1;
        }else {
            context.getSource().sendFeedback(Text.literal("removed " + shortenedPlot + " from the plot coordinates list"));
            return 1;
        }
    }
}
