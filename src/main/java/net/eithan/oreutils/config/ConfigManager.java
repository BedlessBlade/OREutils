package net.eithan.oreutils.config;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigManager {
    String configPath = FabricLoader.getInstance().getConfigDir().toString();
}
