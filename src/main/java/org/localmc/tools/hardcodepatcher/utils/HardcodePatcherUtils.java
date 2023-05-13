package org.localmc.tools.hardcodepatcher.utils;

import org.localmc.tools.hardcodepatcher.HardcodePatcherMod;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardcodePatcherUtils {
    public static ArrayList<String> exportList = new ArrayList<>();
    public static Map<String, Boolean> exported = new HashMap<>();

    public static void addToExportList(String text) {
        if (!exported.getOrDefault(text, false) || !isInExportList(text)) {
            exported.put(text, true);
            exportList.add(text);
        }
    }

    public static boolean isInExportList(String text) {
        return exportList.lastIndexOf(text) != -1;
    }

    private static int compare(String source, String target) {
        int n = source.length();
        int m = target.length();

        if (n == 0) return m;
        if (m == 0) return n;

        int[][] d = new int[n + 1][m + 1];
        int temp;

        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int i = 0; i <= m; i++) d[0][i] = i;

        for (int i = 1; i <= n; i++) {
            char ch1 = source.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                if (ch1 == target.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }

        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        return (one = Math.min(one, two)) < three ? one : three;
    }

    public static float getSimilarityRatio(String source, String target) {
        int max = Math.max(source.length(), target.length());
        return 1 - (float) compare(source, target) / max;
    }

    public static void loadConfig() {
        try {
            HardcodePatcherConfig.readConfig();
            List<String> mods = HardcodePatcherConfig.getMods();
            for (String mod : mods) {
                HardcodePatcherPatch vpp = new HardcodePatcherPatch(mod + ".json");
                try {
                    vpp.read();
                    HardcodePatcherMod.vpps.add(vpp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            HardcodePatcherMod.LOGGER.error("Failed to load config: ", e);
            throw new RuntimeException(e);
        }
    }
}
