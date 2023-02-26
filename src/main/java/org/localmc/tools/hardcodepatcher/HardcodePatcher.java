package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.command.CommandEventHandler;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HardcodePatcher implements ModInitializer {
    public static final String MODID = "hardcodepatcher";
    public static final String patchFileName = "hardcodepatcher.json";
    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("HardcodePatcher");
    public static final Logger LOGGER = LogManager.getLogger();
    public static List<HardcodePatcherPatch> vpps = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandEventHandler::registerClientCommands);

        try {
            HardcodePatcherConfig.readConfig();
            List<String> mods = HardcodePatcherConfig.getMods();
            for (String mod : mods) {
                HardcodePatcherPatch vpp = new HardcodePatcherPatch(mod + ".json");
                try {
                    vpp.readConfig();
                    vpps.add(vpp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config: ", e);
            throw new RuntimeException(e);
        }
    }
/*
    public static final class Events {
        public static void loadConfig() {
                try {
                    HardcodeTextPatcherConfig.readConfig();
                    List<String> mods = HardcodeTextPatcherConfig.getMods();
                    for (String mod : mods) {
                        HardcodeTextPatcherConfig vpp = new HardcodeTextPatcherConfig(mod + ".json");
                        try {
                            vpp.readConfig();
                            vpps.add(vpp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    LOGGER.error("Failed to load config: ", e);
                    throw new RuntimeException(e);
                }
        }
    }
 */
}
