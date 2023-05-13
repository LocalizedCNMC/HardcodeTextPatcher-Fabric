package org.localmc.tools.hardcodepatcher.mixin;

import org.localmc.tools.hardcodepatcher.utils.ThePatcher;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Mixin(BaseText.class)
public abstract class BaseTextMixin {

    @Shadow
    @Final
    protected List<Text> siblings;

    @Shadow
    public abstract MutableText copy();

    @Shadow
    public abstract Style getStyle();

    @ModifyArg(method = "asOrderedText", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Language;reorder(Lnet/minecraft/text/StringVisitable;)Lnet/minecraft/text/OrderedText;"))
    private StringVisitable proxy_asOrderedText(StringVisitable text1) {
        if (text1 instanceof TranslatableText) return text1;
        if (text1 instanceof LiteralText text) {
            String c = ThePatcher.patch(text.getString(), "BaseText#asOrderedText");

            if (c != null && !c.equals("")) {
                return new LiteralText(c).setStyle(this.getStyle());
            }
        }
        return text1;
    }
    @Inject(method = "append", at = @At("HEAD"), cancellable = true)
    private void proxy_append(Text text2, CallbackInfoReturnable<MutableText> cir) {
        if (text2 instanceof TranslatableText) return;
        if (text2 instanceof LiteralText text) {
            String c = ThePatcher.patch(text.getString(), "BaseText#append");

            if (c != null && !c.equals("")) {
                this.siblings.add(new LiteralText(c).setStyle(text.getStyle()));
                cir.setReturnValue(this.copy());
            }
        }
    }
}
