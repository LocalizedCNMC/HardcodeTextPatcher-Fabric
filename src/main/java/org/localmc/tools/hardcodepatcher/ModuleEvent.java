package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;

import java.io.IOException;
import java.util.List;

public class ModuleEvent {
     static void loadConfig() {
        try {
            HardcodePatcherConfig.readConfig();
            List<String> mods = HardcodePatcherConfig.getMods();
            for (String mod : mods) {
                HardcodePatcherPatch vpp = new HardcodePatcherPatch(mod + ".json");
                try {
                    vpp.read();
                    HardcodePatcher.vpps.add(vpp);
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
