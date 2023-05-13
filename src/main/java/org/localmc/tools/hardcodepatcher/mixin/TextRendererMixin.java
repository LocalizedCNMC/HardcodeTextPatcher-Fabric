package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.utils.ThePatcher;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    // String
    @ModifyArg(
            method = "drawInternal(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZIIZ)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawLayer(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F"
            ),
            index = 0
    )

    private String proxy_drawInternal(String text) {
        String c = ThePatcher.patch(text, "TextRenderer#drawInternal(String)");
        if (c != null && !c.equals("")) {
            return c;
        }
        return text;
    }
}
