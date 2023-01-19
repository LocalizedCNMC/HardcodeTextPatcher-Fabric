package me.fengming.vaultpatcher.mixin;

import me.fengming.vaultpatcher.ThePatcher;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//import static me.fengming.vaultpatcher.VaultPatcher.exportList;

@Mixin(value = LiteralText.class, priority = Integer.MAX_VALUE)
public abstract class LiteralTextMixin {
    @Accessor("text")
    abstract String getText1();

    @Inject(method = "asString", at = @At("HEAD"), cancellable = true)
    private void proxy_asString(CallbackInfoReturnable<String> cir) {
        String c = ThePatcher.patch(this.getText1());
        if (c != null) cir.setReturnValue(c);
    }
    @Inject(method = "getRawString", at = @At("HEAD"), cancellable = true)
    private void proxy_getRawString(CallbackInfoReturnable<String> cir) {
        String c = ThePatcher.patch(this.getText1());
        if (c != null) cir.setReturnValue(c);
    }

}

