package net.eithan.oreutils.mixin;

import net.eithan.oreutils.config.ModConfigs;
import net.eithan.oreutils.events.EndClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin{

    @Shadow protected abstract void renderLabelIfPresent(AbstractClientPlayerEntity abstractClientPlayerEntity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, float f);

    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void modifiedRender(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null)
            return;

        if(ModConfigs.blockListContains(abstractClientPlayerEntity.getName().getString())) {
            if(EndClientTickEvent.kickTimer == 0) {
                Vec3d blockedPlayerPosition = abstractClientPlayerEntity.getPos();
                for (int[] plot : ModConfigs.PLOT_COORDINATES) {
                    if (blockedPlayerPosition.x <= plot[0] && blockedPlayerPosition.x >= plot[2] || blockedPlayerPosition.x <= plot[2] && blockedPlayerPosition.x >= plot[0]) {
                        if (blockedPlayerPosition.z <= plot[1] && blockedPlayerPosition.z >= plot[3] || blockedPlayerPosition.z <= plot[3] && blockedPlayerPosition.z >= plot[1]) {
                            player.networkHandler.sendChatMessage("/p kick " + abstractClientPlayerEntity.getName().getString());
                            player.sendMessage(Text.literal("kicked player " + abstractClientPlayerEntity.getName().getString()));
                            EndClientTickEvent.kickTimer = 40;
                        }
                    }
                }
            }

            if(ModConfigs.HIDE_BLOCKED_PLAYERS) {
                renderLabelIfPresent(abstractClientPlayerEntity, abstractClientPlayerEntity.getDisplayName(), matrixStack, vertexConsumerProvider, i, f);
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V"), index = 1)
    private Text modifiedLabelRenderArgsMixin(Text in) {
        if(ModConfigs.NAME_TAG_CENSOR && ModConfigs.blockListContains(in.getString()))
            return Text.literal("-CENSORED-");
        else
            return in;
    }
}
