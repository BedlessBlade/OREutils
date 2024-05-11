package net.eithan.oreutils.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class EndClientTickEvent implements ClientTickEvents.EndTick{

    public static int kickTimer = 0;
    public static int wbTimer = 0;

    @Override
    public void onEndTick(MinecraftClient client) {
        if (kickTimer > 0) {
            kickTimer -= 1;
        }
        if(wbTimer > 0) {
            wbTimer -= 1;
        } else if(client.player != null){
            client.player.networkHandler.sendChatMessage("wb");
        }
    }
}
