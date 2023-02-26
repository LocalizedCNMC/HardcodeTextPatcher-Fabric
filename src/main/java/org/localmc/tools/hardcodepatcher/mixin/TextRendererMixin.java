package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.ThePatcher;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;

@Mixin(value = TextRenderer.class)
public class TextRendererMixin {
    //GUI Transcription
    @ModifyArgs(
            method = {
                    "drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I",
                    "drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFIZ)I",
                    "draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;draw(Ljava/lang/String;FFILnet/minecraft/util/math/Matrix4f;ZZ)I"
            ))
    private String proxy_draw(String text) {
        String c = ThePatcher.patch(text);
        c = c == null ? text : c;
        return c;
    }
}
