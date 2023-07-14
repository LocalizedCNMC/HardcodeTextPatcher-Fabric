package me.fengming.vaultpatcher;

import com.mojang.logging.LogUtils;
import me.fengming.vaultpatcher.config.VaultPatcherConfig;
import me.fengming.vaultpatcher.config.VaultPatcherPatch;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class VaultPatcher {
    public static final String MODID = "vaultpatcher";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init(Path mcPath) {
        try {
            VaultPatcher.LOGGER.warn("[VaultPatcher] Loading Config!");
            VaultPatcherConfig.readConfig(mcPath.resolve("config").resolve("vaultpatcher_asm"));
            List<String> mods = VaultPatcherConfig.getMods();
            for (String mod : mods) {
                VaultPatcherPatch vpp = new VaultPatcherPatch(mod + ".json");
                try {
                    vpp.read();
                    Utils.vpps.add(vpp);
                    Utils.translationInfos.addAll(vpp.getTranslationInfoList());
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
