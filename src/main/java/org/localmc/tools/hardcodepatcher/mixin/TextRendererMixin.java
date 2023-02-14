package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.ThePatcher;
import net.minecraft.client.font.TextRenderer;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = TextRenderer.class, priority = -Integer.MAX_VALUE)
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
    private void proxy_draw(Args args) {
        HardcodePatcher.exportList.add(args.get(0));
        //Modify String
        String modifyString = ThePatcher.patch(args.get(0));
        modifyString = modifyString == null ? args.get(0) : modifyString;
        //Modify Pos
        //Coming Soon
        args.set(0, modifyString);
    }
}
