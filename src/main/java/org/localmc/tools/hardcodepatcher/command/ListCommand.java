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
        context.getSource().sendFeedback(new TranslatableText("commands.vaultpatcher.list.warning.wip"), false);
        context.getSource().sendFeedback(new TranslatableText("commands.vaultpatcher.list.tips.modslist"), false);
        List<String> mods = HardcodeTextPatcherConfig.getMods();
        for (String mod : mods) {
            context.getSource().sendFeedback(new LiteralText("§a" + mod), false);
        }
        return 0;
    }
}
