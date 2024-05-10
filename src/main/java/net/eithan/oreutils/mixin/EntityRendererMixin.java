package net.eithan.oreutils.mixin;

import net.eithan.oreutils.config.ModConfigs;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin{
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V"), index = 1)
    private Text modifiedLabelRenderArgsMixin(Text in) {
        if(ModConfigs.NAME_TAG_CENSOR && ModConfigs.blockListContains(in.getString()))
            return Text.literal("-CENSORED-");
        else
            return in;
    }
}
