package me.fengming.vaultpatcher;

import com.mojang.logging.LogUtils;
import me.fengming.vaultpatcher.command.CommandEventHandler;
import me.fengming.vaultpatcher.config.VaultPatcherConfig;
import me.fengming.vaultpatcher.config.VaultPatcherPatch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VaultPatcher implements ModInitializer {
    public static final String MODID = "vaultpatcher";
    public static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("HardcodePatcher");
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ArrayList<String> exportList = new ArrayList<>();
    public static List<VaultPatcherPatch> vpps = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CommandEventHandler::registerClientCommands);

        try {
            VaultPatcherConfig.readConfig();
            List<String> mods = VaultPatcherConfig.getMods();
            for (String mod : mods) {
                VaultPatcherPatch vpp = new VaultPatcherPatch(mod + ".json");
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
                    VaultPatcherConfig.readConfig();
                    List<String> mods = VaultPatcherConfig.getMods();
                    for (String mod : mods) {
                        VaultPatcherPatch vpp = new VaultPatcherPatch(mod + ".json");
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
