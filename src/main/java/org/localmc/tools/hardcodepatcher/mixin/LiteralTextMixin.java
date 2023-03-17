package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.ThePatcher;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LiteralText.class)
public abstract class LiteralTextMixin {

    @Mutable
    @Shadow
    @Final
    private String string;

    @Accessor("string")
    abstract String getString_();

    @Inject(method = "asString", at = @At("HEAD"), cancellable = true)
    private void proxy_asString(CallbackInfoReturnable<String> cir) {
        String c = ThePatcher.patch(this.getString_(), "LiteralText#asString");
        if (c != null && !c.equals("")) {
            this.string = c;
            cir.setReturnValue(c);
        }
    }
    @Inject(method = "getRawString", at = @At("HEAD"), cancellable = true)
    private void proxy_getRawString(CallbackInfoReturnable<String> cir) {
        String c = ThePatcher.patch(this.getString_(), "LiteralText#getRawString");
        if (c != null && !c.equals("")) {
            this.string = c;
            cir.setReturnValue(c);
        }
    }

}

