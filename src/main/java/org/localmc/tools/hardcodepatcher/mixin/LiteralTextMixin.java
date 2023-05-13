package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.utils.ThePatcher;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiteralText.class)
public abstract class LiteralTextMixin {

    @Mutable
    @Shadow
    @Final
    private String string;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void proxy_init(String pText, CallbackInfo ci) {
        String c = ThePatcher.patch(this.string, "LiteralText#init");
        if (c == null) {
            this.string = pText;
        } else this.string = c;
    }

}

