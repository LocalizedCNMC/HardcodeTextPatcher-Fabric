package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.text.*;
import org.localmc.tools.hardcodepatcher.HardcodePatcherMod;
import net.minecraft.server.command.ServerCommandSource;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;
import org.localmc.tools.hardcodepatcher.config.PatchInfo;

public class ListCommand implements Command<ServerCommandSource> {
    public static ListCommand instance = new ListCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.list.tips.modslist"), false);
        for (HardcodePatcherPatch vpp : HardcodePatcherMod.vpps) {
            PatchInfo info = vpp.getInfo();
            context.getSource().sendFeedback(constructText(info, vpp.getPname()), false);
        }
        return 0;
    }

    private MutableText constructText(PatchInfo info, String pname) {
        return new LiteralText(pname).setStyle(
                Style.EMPTY.withColor(TextColor.fromRgb(0x55FF55))
                        .withHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                new LiteralText("")
                                        .append(new TranslatableText("commands.hardcodepatcher.list.tips.name", info.getName()).append("\n")
                                                    .append(new TranslatableText("commands.hardcodepatcher.list.tips.desc", info.getDesc()).append("\n")
                                                            .append(new TranslatableText("commands.hardcodepatcher.list.tips.authors", info.getAuthors()).append("\n")
                                                                    .append(new TranslatableText("commands.hardcodepatcher.list.tips.mods", info.getMods()
                                                                )))))
                        ))
        );
    }
}
