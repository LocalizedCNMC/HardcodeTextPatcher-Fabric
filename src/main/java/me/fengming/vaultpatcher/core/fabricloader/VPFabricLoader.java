package me.fengming.vaultpatcher.core.fabricloader;

import me.fengming.vaultpatcher.VaultPatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class VPFabricLoader implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VaultPatcher.LOGGER.warn("[VaultPatcher] Warning! You are in ASM mode!");
        VaultPatcher.LOGGER.warn("[VaultPatcher] In this mode, the configuration files will be stored in \"config/vaultpatcher_asm\"!");
        VaultPatcher.LOGGER.warn("[VaultPatcher] Loading VPTransformationService!");
        Path minecraftPath = FabricLoader.getInstance().getGameDir();
        if (minecraftPath == null) {
            VaultPatcher.LOGGER.warn("Minecraft path not found");
            return;
        }
        VaultPatcher.init(minecraftPath);
    }
}
