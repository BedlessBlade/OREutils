package net.eithan.oreutils.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.eithan.oreutils.config.ModConfigs;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class PlotCoordsRemoveSuggestionProvider implements SuggestionProvider<FabricClientCommandSource>{
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        if(ModConfigs.PLOT_COORDINATES.isEmpty())
            return Suggestions.empty();

        for(int[] value : ModConfigs.PLOT_COORDINATES)
            builder.suggest("\"" + Arrays.toString(value).replace(" ", "") + "\"");

        return builder.buildFuture();
    }
}

