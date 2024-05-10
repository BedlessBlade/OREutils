package net.eithan.oreutils.mixin;

import net.eithan.oreutils.config.ModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin{

    @Shadow protected abstract void renderLabelIfPresent(AbstractClientPlayerEntity abstractClientPlayerEntity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, float f);

    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void modifiedRender(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null)
            return;
        if(ModConfigs.blockListContains(abstractClientPlayerEntity.getName().getString()) && ModConfigs.HIDE_BLOCKED_PLAYERS)
            ci.cancel();
        Text name_tag = ModConfigs.NAME_TAG_CENSOR ? Text.literal("-CENSORED-") : abstractClientPlayerEntity.getDisplayName();
        renderLabelIfPresent(abstractClientPlayerEntity, name_tag, matrixStack, vertexConsumerProvider, i, f);
    }
}
