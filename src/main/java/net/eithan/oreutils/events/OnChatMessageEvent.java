package net.eithan.oreutils.events;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;

public class OnChatMessageEvent implements ClientReceiveMessageEvents.AllowGame{
    final String[] RANKS = {"Visitor", "Student", "Builder", "Engineer", "Mod", "Admin", "Discord"};
    @Override
    public boolean allowReceiveGameMessage(Text message, boolean overlay) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null)
            return true;
        String[] messagePrefix = message.getString().split(":")[0].split(" ");
        if(messagePrefix.length != 3)
            return true;
        if(!Arrays.asList(RANKS).contains(messagePrefix[0]))
            return true;
//        for(String blocked_word : BlockModClientConfigs.Blocked_Words.get()){
//            if(message[2].toLowerCase().contains(blocked_word)){
//                event.setCanceled(true);
//                return;
//            }
//        }
        player.sendMessage(Text.literal("you got a message dickhead"));
        return true;
    }
}
