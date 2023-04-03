package org.localmc.tools.hardcodepatcher;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.localmc.tools.hardcodepatcher.command.CommandEventHandler;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HardcodePatcher implements ModInitializer {
    public static final String MODID = "hardcodepatcher";
    public static final String patchFileName = "langpatcher.json";
    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("HardcodePatcher");
    public static final Logger LOGGER = LogUtils.getLogger();
    public static List<HardcodePatcherPatch> vpps = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandEventHandler::registerClientCommands);
        HPConfigRegistrationCallback.EVENT.register(this::loadConfig);
    }

    public void loadConfig(HardcodePatcherPatch hpp, String json) {
        List<HardcodePatcherPatch> hpps = HardcodePatcher.vpps;
        json = ".json";
        try {
            HardcodePatcherConfig.readConfig();
            List<String> mods = HardcodePatcherConfig.getMods();
            for (String mod : mods) {
                hpp = new HardcodePatcherPatch(mod + json);
                try {
                    hpp.read();
                    hpps.add(hpp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            HardcodePatcher.LOGGER.error("Failed to load config: ", e);
            throw new RuntimeException(e);
        }
    }
}
