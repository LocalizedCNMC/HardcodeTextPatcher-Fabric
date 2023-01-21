package org.localmc.tools.hardcodepatcher.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.localmc.tools.hardcodepatcher.HardcodeTextPatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ExportCommand implements Command<ServerCommandSource> {
    public static ExportCommand instance = new ExportCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.export.warning.wip"), true);
        Gson gson = new Gson();
        String json = gson.toJson(HardcodeTextPatcher.exportList, new TypeToken<ArrayList<String>>() {
        }.getType());
        //Export langs
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(FabricLoader.getInstance().getGameDir().resolve(HardcodeTextPatcher.patchFileName).toFile()));
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
