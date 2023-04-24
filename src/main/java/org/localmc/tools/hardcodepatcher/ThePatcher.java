package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.config.DebugMode;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;

import java.util.Arrays;

public class ThePatcher {
    public static String patch(String string, String method) {
        if (string == null || string.equals("")) {
            return string;
        }

        if (!HardcodePatcherConfig.getOptimize().isDisableExport()) {
            HardcodePatcherUtils.addToExportList(string);
        }

        String ret;
        for (HardcodePatcherPatch vpp : HardcodePatcher.vpps) {
            StackTraceElement[] stacks = null;
            if (!HardcodePatcherConfig.getOptimize().isDisableStacks()) {
                stacks = Thread.currentThread().getStackTrace();
            }
            ret = vpp.patch(string, stacks);

            DebugMode debug = HardcodePatcherConfig.getDebugMode();

            if (debug.isEnable()) {
                return outputDebugIndo(string, method, ret, stacks, debug);
            } else return ret;

        }
        return string;
    }

    private static String outputDebugIndo(String s, String m, String ret, StackTraceElement[] stacks, DebugMode debug) {
        String format = debug.getOutputFormat();
        if (ret != null && !ret.equals(s)) {
            if (debug.getOutputMode() == 1 || debug.getOutputMode() == 0) {
                HardcodePatcher.LOGGER.info(
                        format.replace("<source>", s)
                                .replace("<target>", ret)
                                .replace("<method>", m)
                                .replace("<stack>", Arrays.toString(stacks))
                );
            }
        } else {
            if (debug.getOutputMode() == 1) {
                HardcodePatcher.LOGGER.info(
                        format.replace("<source>", s)
                                .replace("<target>", s)
                                .replace("<method>", m)
                                .replace("<stack>", Arrays.toString(stacks))
                );
            }
        }
        return ret;
    }
}
