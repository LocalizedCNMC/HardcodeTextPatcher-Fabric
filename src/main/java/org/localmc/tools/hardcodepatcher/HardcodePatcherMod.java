package org.localmc.tools.hardcodepatcher;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.localmc.tools.hardcodepatcher.command.CommandEventHandler;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;
import org.localmc.tools.hardcodepatcher.event.HardcodePatcherRegistrationEvents;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.localmc.tools.hardcodepatcher.utils.HardcodePatcherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HardcodePatcherMod implements ModInitializer {
    public static final String MODID = "hardcodepatcher";
    public static final String MODNAME = "HardcodePatcher";
    public static final String patchFileName = "langpatcher.json";
    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(MODNAME);
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);
    public static List<HardcodePatcherPatch> vpps = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandEventHandler::registerClientCommands);

        HardcodePatcherRegistrationEvents.EVENT.register(HardcodePatcherUtils::loadConfig);
/*
        try {
            HardcodePatcherConfig.readConfig();
            List<String> mods = HardcodePatcherConfig.getMods();
            for (String mod : mods) {
                HardcodePatcherPatch vpp = new HardcodePatcherPatch(mod + ".json");
                try {
                    vpp.read();
                    vpps.add(vpp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config: ", e);
            throw new RuntimeException(e);
        }
 */
    }
}
