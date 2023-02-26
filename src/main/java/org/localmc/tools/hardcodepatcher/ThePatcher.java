package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.config.DebugMode;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;

import java.util.Arrays;

public class ThePatcher {
    public ThePatcher() {
    }

    public static String patch(String s) {
        if (s == null || s.trim().equals("")) {
            return s;
        }
        HardcodePatcherUtils.addToExportList(s);
        // VaultPatcher.LOGGER.info(Arrays.toString(Thread.currentThread().getStackTrace()));
        String ret;
        for (HardcodePatcherPatch vpp : HardcodePatcher.vpps) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            ret = vpp.patch(s, stacks);
            DebugMode debug = HardcodePatcherConfig.getDebugMode();
            String format = debug.getOutputFormat();
            if (ret != null && !ret.equals(s)) {
                if (debug.isEnable() && (debug.getOutputMode() == 1 || debug.getOutputMode() == 0)) {
                    HardcodePatcher.LOGGER.info(
                            format.replace("<source>", s)
                                    .replace("<target>", ret)
                                    .replace("<stack>", Arrays.toString(stacks))
                    );
                }
                return ret;
            } else {
                if (debug.isEnable() && debug.getOutputMode() == 1) {
                    HardcodePatcher.LOGGER.info(
                            format.replace("<source>", s)
                                    .replace("<target>", s)
                                    .replace("<stack>", Arrays.toString(stacks))
                    );
                }
                return s;
            }
        }
        return s;
    }
}
