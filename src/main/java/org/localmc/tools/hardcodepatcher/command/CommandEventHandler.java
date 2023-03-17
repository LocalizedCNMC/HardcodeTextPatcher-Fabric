package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandEventHandler {
    public static void registerClientCommands(CommandDispatcher<ServerCommandSource> dispatcher, Boolean dedicated) {
        LiteralCommandNode<ServerCommandSource> cmd = dispatcher.register(
                CommandManager.literal(HardcodePatcher.MODID
                    ).then(
                            CommandManager.literal("export")
                                .executes(ExportCommand.instance)
                    ).then(
                            CommandManager.literal("list")
                                .executes(ListCommand.instance)
                    ).then(
                            CommandManager.literal("reload")
                                .executes(ReloadCommand.instance)
                ));
        dispatcher.register(CommandManager.literal("hp").redirect(cmd));
    }
}
