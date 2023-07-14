package me.fengming.vaultpatcher.core.fabricloader;

import me.fengming.vaultpatcher.Utils;
import me.fengming.vaultpatcher.VaultPatcher;
import me.fengming.vaultpatcher.config.DebugMode;
import me.fengming.vaultpatcher.config.TranslationInfo;
import me.fengming.vaultpatcher.config.VaultPatcherConfig;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.service.ITreeClassTransformer;

import java.util.Iterator;

public class VPClassTransformer implements PreLaunchEntrypoint, ITreeClassTransformer {
    private final boolean isActive = true;

    public VPClassTransformer() {
        VaultPatcher.LOGGER.warn("[VaultPatcher] Loading VPTransformer!");
    }

    @Override
    public void onPreLaunch() {
        VaultPatcher.LOGGER.warn("[VaultPatcher] Loading VPTransformer!");
    }

    @Override
    public boolean transformClassNode(String name, String transformedName, ClassNode classNode) {
        Iterator<TranslationInfo> it = Utils.getIterator();
        while (it.hasNext()) {
            TranslationInfo info = it.next();
            DebugMode debug = VaultPatcherConfig.getDebugMode();
            if (info.getTargetClassInfo().getName().isEmpty() || classNode.name.equals(info.getTargetClassInfo().getName().replace('.', '/'))) {
                // Method
                methodReplace(classNode, info, debug);
                // Field
                fieldReplace(classNode, info, debug);
            }

            it.remove();
        }
        return isActive;
    }

    @Override
    public String getName() {
        return VaultPatcher.MODID;
    }

    @Override
    public boolean isDelegationExcluded() {
        return false;
    }

    private static void fieldReplace(ClassNode input, TranslationInfo info, DebugMode debug) {
        for (FieldNode field : input.fields) {
            if (field.value instanceof String v && info.getKey().equals(v)) {
                if (debug.isEnable()) {
                    VaultPatcher.LOGGER.warn("[VaultPatcher] Trying replacing!");
                    Utils.outputDebugIndo((String) field.value, "ASMTransformField", info.getValue(), input.name, debug);
                }
                field.value = info.getValue();
            }
        }
    }

    private static void methodReplace(ClassNode input, TranslationInfo info, DebugMode debug) {
        for (MethodNode method : input.methods) {
            String methodName = info.getTargetClassInfo().getMethod();
            if (methodName.isEmpty() || methodName.equals(method.name)) {
                for (AbstractInsnNode instruction : method.instructions) {
                    if (instruction.getType() == AbstractInsnNode.LDC_INSN) { // 字符串常量
                        LdcInsnNode ldcInsnNode = (LdcInsnNode) instruction;
                        if (ldcInsnNode.cst instanceof String v && v.equals(info.getKey())) {
                            if (debug.isEnable()) {
                                VaultPatcher.LOGGER.warn("[VaultPatcher] Trying replacing!");
                                Utils.outputDebugIndo((String) ldcInsnNode.cst, "ASMTransformMethod-Ldc", info.getValue(), input.name, debug);
                            }
                            ldcInsnNode.cst = info.getValue();
                        }
                    } else if (instruction.getType() == AbstractInsnNode.INVOKE_DYNAMIC_INSN) { // 字符串拼接
                        InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) instruction;
                        if (invokeDynamicInsnNode.name.equals("makeConcatWithConstants") && invokeDynamicInsnNode.desc.equals("(I)Ljava/lang/String;")) {
                            for (int i = 0; i < invokeDynamicInsnNode.bsmArgs.length; i++) {
                                if (invokeDynamicInsnNode.bsmArgs[i] instanceof String v) {
                                    v = Utils.removeUnicodeEscapes(v);
                                    if (v.equals(info.getKey())) {
                                        if (debug.isEnable()) {
                                            VaultPatcher.LOGGER.warn("[VaultPatcher] Trying replacing!");
                                            Utils.outputDebugIndo(v, "ASMTransformMethod-Invoke", info.getValue(), input.name, debug);
                                        }
                                        invokeDynamicInsnNode.bsmArgs[i] = info.getValue();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
