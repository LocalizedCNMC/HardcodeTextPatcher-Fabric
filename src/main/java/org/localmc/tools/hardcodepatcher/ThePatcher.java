package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.config.DebugMode;
import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherPatch;
import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherConfig;

import java.util.Arrays;

public class ThePatcher {
    public ThePatcher() {
    }

    public static String patch(String s) {
        if (s == null || s.trim().equals("")) {
            return s;
        }
        HardcodeTextPatcher.exportList.add(s);
        // HardcodeTextPatcher.LOGGER.info(Arrays.toString(Thread.currentThread().getStackTrace()));
        String ret;
        for (HardcodeTextPatcherPatch vpp : HardcodeTextPatcher.vpps) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            ret = vpp.patch(s, stacks);
            DebugMode debug = HardcodeTextPatcherConfig.getDebugMode();
            if (ret != null && !ret.equals(s)) {
                if (debug.isEnable() && debug.getOutputMode() == 0) {
                    HardcodeTextPatcher.LOGGER.info(String.format(debug.getOutputFormat(), s, ret, Arrays.toString(stacks)));
                }
                return ret;
            } else {
                if (debug.isEnable() && debug.getOutputMode() == 1) {
                    HardcodeTextPatcher.LOGGER.info(String.format(debug.getOutputFormat(), s, ret, Arrays.toString(stacks)));
                }
                return s;
            }
        }
        return s;
    }
}
