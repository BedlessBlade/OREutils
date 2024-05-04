package net.eithan.oreutils.events;

import net.eithan.oreutils.config.ModConfigs;
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
//        player.sendMessage(Text.literal(String.valueOf(ModConfigs.configs.get("Plot_Coordinates"))));
//        ModConfigs.addToPlotCoordinates(new int[]{1, 2, 3, 4});
//        ModConfigs.configs.set(new Pair<>("Plot_Coordinates", "[(1,2,3,4)]"));
        String[] messagePrefix = message.getString().split(":")[0].split(" ");
        if(messagePrefix.length != 3)
            return true;
        if(!Arrays.asList(RANKS).contains(messagePrefix[0]))
            return true;
        for(int i = 0; i < ModConfigs.BLOCK_LIST.size(); i++){
            String blocked_word = ModConfigs.BLOCK_LIST.get(i);
            if(messagePrefix[2].toLowerCase().equals(blocked_word.toLowerCase())){
                return false;
            }
        }
        return true;
    }
}
