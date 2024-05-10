package net.eithan.oreutils.config;

import com.mojang.datafixers.util.Pair;
import net.eithan.oreutils.OREUtils;
import net.eithan.oreutils.commands.BlockListCommands;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class ModConfigs {
    public static ConfigManager configs;
    public static ArrayList<String> BLOCK_LIST;
    public static ArrayList<int[]> PLOT_COORDINATES;
    public static boolean HIDE_BLOCKED_MESSAGES;
    public static boolean HIDE_BLOCKED_PLAYERS;
    public static boolean KICK_ON_SIGHT;
    public static boolean AUTO_WB;
    public static boolean NAME_TAG_CENSOR;

    public static boolean blockListContains(String name) {
        for (String blockedName : BLOCK_LIST) {
            if (blockedName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addToBlockList(String name) {
        if(name.isBlank())
            return false;
        String trimmed_name = name.trim();
        for (String blockedName : BLOCK_LIST) {
            if (blockedName.equalsIgnoreCase(trimmed_name)) {
                return false;
            }
        }
        BLOCK_LIST.add(trimmed_name);
        configs.set(new Pair<>("block_list", BLOCK_LIST));
        return true;
    }

    public static boolean removeFromBlockList(String blockedName) {
        for(int i = 0; i < BLOCK_LIST.size() ; i++) {
            if (BLOCK_LIST.get(i).equalsIgnoreCase(blockedName)) {
                BLOCK_LIST.remove(i);
                configs.set(new Pair<>("block_list", BLOCK_LIST));
                return true;
            }
        }
        return false;
    }

    public static String arrayListToString(ArrayList<int[]> array) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i = 0; i < array.size(); i++) {
            if(i > 0)
                sb.append(", ");
            sb.append(Arrays.toString(array.get(i)));
        }
        sb.append(']');
        return sb.toString();
    }

    public static void addToPlotCoordinates(int[] plotCoordinate) {
        PLOT_COORDINATES.add(plotCoordinate);
        configs.set(new Pair<>("plot_coordinates", arrayListToString(PLOT_COORDINATES)));
    }

    public static boolean removeFromPlotCoordinates(int[] plotCoordinate) {
        for(int i = 0; i < PLOT_COORDINATES.size() ; i++) {
            if (Arrays.equals(PLOT_COORDINATES.get(i), plotCoordinate)) {
                PLOT_COORDINATES.remove(i);
                configs.set(new Pair<>("plot_coordinates", arrayListToString(PLOT_COORDINATES)));
                return true;
            }
        }
        return false;
    }

    public static void setHideBlockedMessages(boolean hideBlockedMessages) {
        configs.set(new Pair<>("hide_blocked_messages", hideBlockedMessages));
        HIDE_BLOCKED_MESSAGES = Boolean.parseBoolean(configs.get("hide_blocked_messages"));
    }

    public static void setHideBlockedPlayers(boolean hideBlockedPlayers) {
        configs.set(new Pair<>("hide_blocked_players", hideBlockedPlayers));
        HIDE_BLOCKED_PLAYERS = Boolean.parseBoolean(configs.get("hide_blocked_players"));
    }

    public static void setKickOnSight(boolean kickOnSight) {
        configs.set(new Pair<>("kick_on_sight", kickOnSight));
        KICK_ON_SIGHT = Boolean.parseBoolean(configs.get("kick_on_sight"));
    }

    public static void setAutoWb(boolean autoWb) {
        configs.set(new Pair<>("auto_wb", autoWb));
        AUTO_WB = Boolean.parseBoolean(configs.get("auto_wb"));
    }

    public static void setNameTagCensor(boolean nameTagCensoring) {
        configs.set(new Pair<>("name_tag_censoring", nameTagCensoring));
        NAME_TAG_CENSOR = Boolean.parseBoolean(configs.get("name_tag_censoring"));
    }

    public static void registerConfigs() {
        configs = new ConfigManager(FabricLoader.getInstance().getConfigDir().resolve( OREUtils.MOD_ID + "config" + ".properties" ).toFile(), OREUtils.MOD_ID + "config");

        //this creates the default string for writing to the properties file
        //when writing using this, it uses the values only if the key does not exist in the hashmap
        createConfigs();

        //either creates a new file, or loads the values to the hashmap (only strings)
        configs.SetUp();

        //assigns the actual values (arraylist, boolean etc) to the code accessible variables
        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("block_list", "[]"), "list of blocked usernames");
        configs.addKeyValuePair(new Pair<>("plot_coordinates", "[[0,0,0,0]]"), "list of plot coordinates");
        configs.addKeyValuePair(new Pair<>("hide_blocked_messages", true), "boolean to toggle blocking people in chat or not");
        configs.addKeyValuePair(new Pair<>("hide_blocked_players", true), "boolean to toggle seeing blocked players");
        configs.addKeyValuePair(new Pair<>("kick_on_sight", true), "boolean to toggle kicking blocked people on sight");
        configs.addKeyValuePair(new Pair<>("auto_wb", false), "boolean to toggle the automatic wb feature");
        configs.addKeyValuePair(new Pair<>("name_tag_censoring", false), "boolean to toggle the name tag censoring feature");
    }

    private static void assignConfigs() {
        String block_list_hold = configs.get("block_list");
        BLOCK_LIST = new ArrayList<>(Arrays.stream(block_list_hold.substring(1, block_list_hold.length() - 1).split(", ")).toList());
        if(BLOCK_LIST.size() == 1 && BLOCK_LIST.getFirst().isBlank())
            BLOCK_LIST.removeFirst();
        String plot_coordinates_hold = configs.get("plot_coordinates");
        PLOT_COORDINATES = new ArrayList<>();
        String split_coordinates = plot_coordinates_hold.substring(1, plot_coordinates_hold.length() - 1);
        if(!split_coordinates.isEmpty()) {
            for (String coordinate : split_coordinates.substring(1, split_coordinates.length() - 1).split("], \\[")) {
                PLOT_COORDINATES.add(Arrays.stream(coordinate.split(",")).mapToInt(Integer::parseInt).toArray());
            }
        }
        HIDE_BLOCKED_MESSAGES = Boolean.parseBoolean(configs.get("hide_blocked_messages"));
        HIDE_BLOCKED_PLAYERS = Boolean.parseBoolean(configs.get("hide_blocked_players"));
        KICK_ON_SIGHT = Boolean.parseBoolean(configs.get("kick_on_sight"));
        AUTO_WB = Boolean.parseBoolean(configs.get("auto_wb"));
        NAME_TAG_CENSOR = Boolean.parseBoolean(configs.get("name_tag_censoring"));
    }


//    private static void assignConfigs() {
//        String block_list_hold = CONFIG.getOrDefault("Block_List", "[]");
//        BLOCK_LIST = block_list_hold.substring(1, block_list_hold.length() - 1).split(", ");
//        String plot_coordinates_hold = CONFIG.getOrDefault("Plot_Coordinates", "[(0,0,0,0)]");
//        PLOT_COORDINATES = new ArrayList<>();
//        for(String coordinate : plot_coordinates_hold.substring(2, plot_coordinates_hold.length() - 2).split("\\)\\(")) {
//            PLOT_COORDINATES.add(Arrays.stream(coordinate.split(",")).mapToInt(Integer::parseInt).toArray());
//        }
//        HIDE_BLOCKED_MESSAGES = CONFIG.getOrDefault("hide_blocked_messages", true);
//        HIDE_BLOCKED_PLAYERS = CONFIG.getOrDefault("hide_blocked_players", true);
//        KICK_ON_SIGHT = CONFIG.getOrDefault("kick_on_sight", true);
//        AUTO_WB = CONFIG.getOrDefault("auto_wb", true);
//
//        System.out.println("All configs have been set properly");
//    }
}
