package net.eithan.oreutils.events;

import net.eithan.oreutils.config.ModConfigs;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Random;

public class OnChatMessageEvent implements ClientReceiveMessageEvents.AllowGame{
    final String[] RANKS = {"Visitor", "Student", "Builder", "Engineer", "Mod", "Admin", "Discord"};
    @Override
    public boolean allowReceiveGameMessage(Text message, boolean overlay) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null)
            return true;
        // if autowb is enabled, and if the message is a join message, and if the player-that-has-joined's username isn't on our block list, and if the player isn't us.
        if(ModConfigs.AUTO_WB && message.getString().contains("has joined the network") && !ModConfigs.blockListContains(message.getString().split(" ")[0].substring(2)) && !message.getString().split(" ")[0].substring(2).equals(player.getName().getString())){
            // send an auto wb and return to not do overly many checks
            EndClientTickEvent.wbTimer = new Random().nextInt(40);
            return true;
        }
        if(!ModConfigs.HIDE_BLOCKED_MESSAGES)
            return true;
        // ORE messages are formatted like: "RANK | USERNAME: MESSAGE" if i split at the ":" then using " " i separate the RANK the | and the USERNAME
        String[] messagePrefix = message.getString().split(":")[0].split(" ");
        // if after separation it does not result in 3 Strings something has gone wrong so i do not do anything
        if(messagePrefix.length != 3)
            return true;
        // if the message doesn't have a rank, or doesn't have a "|" i do not do anything.
        if(!Arrays.asList(RANKS).contains(messagePrefix[0]) || !messagePrefix[1].equals("|"))
            return true;
        // finally return false if the blocked player's username is in the message, or true if it isn't.
        return !ModConfigs.blockListContains(messagePrefix[2]);
    }
}
