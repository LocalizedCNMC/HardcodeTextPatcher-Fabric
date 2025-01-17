package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class ReloadCommand implements Command<ServerCommandSource> {
    public static ReloadCommand instance = new ReloadCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        MinecraftClient.getInstance().reloadResources();
        return 0;
    }
}
