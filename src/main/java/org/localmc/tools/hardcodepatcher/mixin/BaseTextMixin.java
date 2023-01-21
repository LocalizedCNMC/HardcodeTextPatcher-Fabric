package org.localmc.tools.hardcodepatcher.mixin;


import net.minecraft.text.*;
import org.localmc.tools.hardcodepatcher.ThePatcher;
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
    /*
    @Accessor("siblings")
    abstract List<Component> getSiblings();
    @Inject(method = "getSiblings", at = @At("HEAD"), cancellable = true)
    private void proxy_getSiblings(CallbackInfoReturnable<List<Component>> cir) {
        List<Component> list = this.getSiblings();
        if (!list.isEmpty()) {
            list.replaceAll(component -> {
                if (component instanceof TextComponent) {
                    return new TextComponent(ThePatcher.patch(component.getContents()));
                } else return component;
            });
        }
        cir.setReturnValue(list);
    }
    */

    @Shadow
    @Final
    protected List<Text> siblings;

    @Shadow
    public abstract MutableText copy();

    @ModifyArg(method = "asOrderedText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Language;reorder(Lnet/minecraft/text/StringVisitable;)Lnet/minecraft/text/OrderedText;"
            )
    )
    private StringVisitable proxy_asOrderedText(StringVisitable p_128116_) {
        if (p_128116_ instanceof BaseText) {
            String c = ThePatcher.patch(p_128116_.getString());
            return new LiteralText(c);
        }
        return p_128116_;
    }

    @Inject(method = "append", at = @At("HEAD"), cancellable = true)
    private void proxy_append(Text p_230529_1_, CallbackInfoReturnable<MutableText> cir) {
        String c = ThePatcher.patch(p_230529_1_.getString());
        this.siblings.add(new LiteralText(c));
        cir.setReturnValue(this.copy());
    }

}
