package net.eithan.oreutils;

import net.eithan.oreutils.config.ModConfigs;
import net.eithan.oreutils.events.EndClientTickEvent;
import net.eithan.oreutils.events.OnChatMessageEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.eithan.oreutils.commands.*;

public class OREUtils implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "oreutils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Initializing OREUtils!");
		ModConfigs.registerConfigs();
		LOGGER.info("Registering Configs.");
		registerCommands();
		LOGGER.info("Registered All Commands.");
		registerEvents();
		LOGGER.info("Registered All Events.");
		LOGGER.info("Finished Initializing OREUtils!");
	}

	public static void registerCommands() {
		ClientCommandRegistrationCallback.EVENT.register(BuildCommand::register);
		ClientCommandRegistrationCallback.EVENT.register(SchoolCommand::register);
		ClientCommandRegistrationCallback.EVENT.register(CompCommand::register);
		ClientCommandRegistrationCallback.EVENT.register(SeasonalCommand::register);
		ClientCommandRegistrationCallback.EVENT.register(BlockListCommands::register);
		ClientCommandRegistrationCallback.EVENT.register(BooleanCommands::register);
		ClientCommandRegistrationCallback.EVENT.register(PlotCoordsCommands::register);
	}

	public static void registerEvents() {
		ClientReceiveMessageEvents.ALLOW_GAME.register(new OnChatMessageEvent());
		ClientTickEvents.END_CLIENT_TICK.register(new EndClientTickEvent());
	}
}