package org.localmc.tools.hardcodepatcher;

import org.localmc.tools.hardcodepatcher.config.HardcodeTextPatcherPatch;

public class ThePatcher {
    public ThePatcher() {
    }

    public static String patch(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        HardcodeTextPatcher.exportList.add(s);
        // HardcodeTextPatcher.LOGGER.info(Arrays.toString(Thread.currentThread().getStackTrace()));
        String ret = s;
        for (HardcodeTextPatcherPatch vpp : HardcodeTextPatcher.vpps) {
            ret = vpp.patch(s, Thread.currentThread().getStackTrace());
            if (ret != null && !ret.equals(s)) {
                return ret;
            }
        }
        return s;
    }
}
