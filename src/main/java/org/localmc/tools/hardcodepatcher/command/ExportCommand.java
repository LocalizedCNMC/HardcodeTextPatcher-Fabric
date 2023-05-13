package org.localmc.tools.hardcodepatcher.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.localmc.tools.hardcodepatcher.HardcodePatcherMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import org.localmc.tools.hardcodepatcher.utils.HardcodePatcherUtils;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ExportCommand implements Command<ServerCommandSource> {
    public static ExportCommand instance = new ExportCommand();

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        Gson gson = new Gson();
        String json = gson.toJson(HardcodePatcherUtils.exportList, new TypeToken<ArrayList<String>>() {
        }.getType());

        if (HardcodePatcherConfig.getOptimize().isDisableExport()) {
            context.getSource().sendError(new TranslatableText("commands.hardcodepatcher.export.tips.disabled"));
            return 1;
        }

        //Export
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                            FabricLoader.getInstance().getGameDir().resolve(HardcodePatcherMod.patchFileName).toFile(),
                            StandardCharsets.UTF_8));
            bw.write(json);
            bw.flush();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.getSource().sendFeedback(new TranslatableText("commands.hardcodepatcher.export.tips.success" + HardcodePatcherMod.patchFileName), true);
        return 0;
    }
}
