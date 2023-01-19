package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.ThePatcher;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
//import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;
//import java.util.Optional;

//import static org.localmc.tools.hardcodepatcher.HardcodeTextPatcher.exportList;

@Mixin(value = TextRenderer.class, priority = Integer.MAX_VALUE)
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
    private String proxy_drawLayer(String p_92898_) {
        return Objects.requireNonNullElse(ThePatcher.patch(p_92898_), p_92898_);
    }
}
