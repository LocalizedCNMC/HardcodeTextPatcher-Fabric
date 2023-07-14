package me.fengming.vaultpatcher.core.modlauncher;

import cpw.mods.modlauncher.api.ITransformer;
import me.fengming.vaultpatcher.Utils;
import me.fengming.vaultpatcher.config.TranslationInfo;
import me.fengming.vaultpatcher.config.VaultPatcherConfig;
import me.fengming.vaultpatcher.config.VaultPatcherPatch;

import java.util.ArrayList;
import java.util.List;

public class ModLauncherUtils {
    public static List<ITransformer.Target> addTargetClasses() {
        List<ITransformer.Target> list = new ArrayList<>();
        VaultPatcherConfig.getClasses().forEach(s -> list.add(ITransformer.Target.targetClass(s.replace(".", "/"))));
        for (VaultPatcherPatch vpp : Utils.vpps) {
            for (TranslationInfo translationInfo : vpp.getTranslationInfoList()) {
                list.add(ITransformer.Target.targetClass(translationInfo.getTargetClassInfo().getName().replace(".", "/")));
            }
        }
        return list;
    }
}
