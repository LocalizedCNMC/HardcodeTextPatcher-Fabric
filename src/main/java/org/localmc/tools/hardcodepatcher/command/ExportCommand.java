package org.localmc.tools.hardcodepatcher.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ExportCommand implements Command<ServerCommandSource> {
    public static ExportCommand instance = new ExportCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.export.warning.wip"), true);
        Gson gson = new Gson();
        String json = gson.toJson(HardcodePatcher.exportList, new TypeToken<ArrayList<String>>() {
        }.getType());
        //Export langs
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                            FabricLoader.getInstance().getGameDir().resolve(HardcodePatcher.patchFileName).toFile(),
                            StandardCharsets.UTF_8));
            bw.write(json);
            bw.flush();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.export.tips.success"), true);
        return 0;
    }
}
