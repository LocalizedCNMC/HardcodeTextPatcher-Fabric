package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.CommandDispatcher;
import org.localmc.tools.hardcodepatcher.HardcodeTextPatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandEventHandler {
    public static void registerClientCommands(CommandDispatcher<ServerCommandSource> dispatcher, Boolean dedicated) {
        dispatcher.register(
                CommandManager.literal(HardcodeTextPatcher.MODID
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
