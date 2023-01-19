package me.fengming.vaultpatcher.command;

import com.mojang.brigadier.CommandDispatcher;
import me.fengming.vaultpatcher.VaultPatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandEventHandler {
    public static void registerClientCommands(CommandDispatcher<ServerCommandSource> dispatcher, Boolean dedicated) {
        dispatcher.register(
                CommandManager.literal(VaultPatcher.MODID
                ).then(
                        CommandManager.literal("export")
                                .executes(ExportCommand.instance)
                ).then(
                        CommandManager.literal("list")
                                .executes(ListCommand.instance)
                ).then(
                        CommandManager.literal("reload")
                                .executes(ReloadCommand.instance)
                )
        );
    }
}
