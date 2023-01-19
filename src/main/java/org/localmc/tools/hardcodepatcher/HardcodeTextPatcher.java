package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.command.CommandEventHandler;
import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherPatch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HardcodeTextPatcher implements ModInitializer {
    public static final String MODID = "vaultpatcher";
    public static final String patchFileName = "localpatcher.json";
    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("HardcodePatcher");
    public static final Logger LOGGER = LogManager.getLogger();
    public static ArrayList<String> exportList = new ArrayList<>();
    public static List<HardcodeTextPatcherPatch> vpps = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandEventHandler::registerClientCommands);

        try {
            HardcodeTextPatcherConfig.readConfig();
            List<String> mods = HardcodeTextPatcherConfig.getMods();
            for (String mod : mods) {
                HardcodeTextPatcherPatch vpp = new HardcodeTextPatcherPatch(mod + ".json");
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
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Events {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void loadConfig(FMLConstructModEvent event) {
            event.enqueueWork(() -> {
                try {
                    HardcodeTextPatcherConfig.readConfig();
                    List<String> mods = HardcodeTextPatcherConfig.getMods();
                    for (String mod : mods) {
                        HardcodeTextPatcherPatch vpp = new HardcodeTextPatcherPatch(mod + ".json");
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
            });
        }
    }
 */
}
