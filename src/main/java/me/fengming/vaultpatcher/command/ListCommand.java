package me.fengming.vaultpatcher.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.fengming.vaultpatcher.config.VaultPatcherConfig;
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
        List<String> mods = VaultPatcherConfig.getMods();
        StringBuilder smods = new StringBuilder();
        for (String mod : mods) {
            smods.append(mod).append(", ");
        }
        context.getSource().sendFeedback(new LiteralText(new String(smods)), false);
        return 0;
    }
}
