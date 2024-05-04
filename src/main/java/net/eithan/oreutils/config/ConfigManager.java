package net.eithan.oreutils.config;

/*
 * class for managing a .properties config file, some functions are taken from magistermaks's SimpleConfig.
 * but this is a very heavily modified version of it.
 */

import com.mojang.datafixers.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger("ConfigManager");
    private final HashMap<String, String> config = new HashMap<>();

    private boolean broken = false;

    /**
     * put here your first line comment for the config file always end with at least 1 \n
     */
    private String configContents = "# Config file for the OREutils mod\n\n";

    private final File file;
    private final String filename;

    public ConfigManager(File file, String filename) {
        this.file = file;
        this.filename = filename;
        config.put(configContents, "");
    }

    public void addKeyValuePair(Pair<String, ?> keyValuePair, String comment) {
        config.put(keyValuePair.getFirst().toLowerCase(), keyValuePair.getSecond().toString());
        configContents += "# " + comment + " | default: " + keyValuePair.getSecond() + "\n" + keyValuePair.getFirst().toLowerCase() + " = " + keyValuePair.getSecond() + "\n\n";
    }

    public void SetUp() {
        String identifier = "Config '" + filename + "'";

        if( !file.exists() ) {
            LOGGER.info( identifier + " is missing, generating default one..." );

            try {
                createConfig();
            } catch (IOException e) {
                LOGGER.error( identifier + " failed to generate!" );
                LOGGER.trace( e );
                broken = true;
            }
        }else{
            try {
                loadConfig();
            } catch (Exception e) {
                LOGGER.error( identifier + " failed to load!" );
                LOGGER.trace( e );
                broken = true;
            }
        }
    }

    private void createConfig() throws IOException {
        // try creating missing files
        file.getParentFile().mkdirs();
        Files.createFile( file.toPath() );

        // write default config data
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        writer.write( configContents );
        writer.close();

    }

    private void loadConfig() throws IOException {
        Scanner reader = new Scanner( file );
        for( int line = 1; reader.hasNextLine(); line ++ ) {
            parseConfigEntry( reader.nextLine(), line );
        }
    }

    private void saveConfig() throws IOException {
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        for (String line : configContents.split("\n")) {
            if(line.startsWith("# ") || line.isEmpty())
                writer.println(line);
            else{
                String key = line.split(" = ")[0];
                writer.println(key + " = " + config.get(key) + "\n");
            }
        }
        writer.close();
    }

    public String get(String key) {
        return config.get(key.toLowerCase());
    }

    public void set(Pair<String, ?> keyValuePair) {
        if(config.get(keyValuePair.getFirst().toLowerCase()) == null)
            return;
        config.put(keyValuePair.getFirst(), keyValuePair.getSecond().toString());
        try {
            saveConfig();
            LOGGER.info("saved config!");
        }catch(Exception e) {
            LOGGER.error("failed to save config!");
            LOGGER.trace( e );
            broken = true;
        }
    }

    private void parseConfigEntry( String entry, int line ) {
        if( !entry.isEmpty() && !entry.startsWith( "#" ) ) {
            String[] parts = entry.split(" = ", 2);
            if( parts.length == 2 ) {
                config.put(parts[0], parts[1]);
            }else{
                throw new RuntimeException("Syntax error in config file on line " + line + "!");
            }
        }
    }
}
