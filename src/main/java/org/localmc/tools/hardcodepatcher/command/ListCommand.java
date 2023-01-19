package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class ListCommand implements Command<ServerCommandSource> {
    public static ListCommand instance = new ListCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.list.warning.wip"), false);
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.list.tips.modslist"), false);
        List<String> mods = HardcodeTextPatcherConfig.getMods();
        StringBuilder smods = new StringBuilder();
        for (String mod : mods) {
            smods.append(mod).append(", ");
        }
        context.getSource().sendFeedback(new LiteralText(new String(smods)), false);
        return 0;
    }
}
