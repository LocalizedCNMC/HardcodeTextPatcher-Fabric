package org.localmc.tools.hardcodepatcher.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.localmc.tools.hardcodepatcher.HardcodePatcherMod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HardcodePatcherConfig {
    private static final Gson GSON = new Gson();
    private static final Path configFile = HardcodePatcherMod.configPath.resolve("config.json");
    private static List<String> mods = new ArrayList<>();
    private static final DebugMode debug = new DebugMode();
    private static final OptimizeParams optimize = new OptimizeParams();

    public static List<String> getMods() {
        return mods;
    }

    public static DebugMode getDebugMode() {
        return debug;
    }

    public static OptimizeParams getOptimize() {
        return optimize;
    }

    private static void writeConfig(JsonWriter jw) throws IOException {
        debug.writeJson(jw);
        jw.name("mods").beginArray();
        jw.name("mods").endArray();
        optimize.writeJson(jw);
    }

    public static void readConfig() throws IOException {
        File f = configFile.toFile();
        if (Files.notExists(configFile)) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            Files.createFile(configFile);
            JsonWriter jw = GSON.newJsonWriter(Files.newBufferedWriter(configFile, StandardCharsets.UTF_8));
            writeConfig(jw);
        }

        JsonReader jr = GSON.newJsonReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

        jr.beginObject();
        while (jr.peek() != JsonToken.END_OBJECT) {
            switch (jr.nextName()) {
                case "debug_mode" -> {
                    if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                        debug.readJson(jr);
                    }
                }
                case "mods" -> {
                    if (jr.peek() == JsonToken.BEGIN_ARRAY) {
                        mods = GSON.fromJson(jr, new TypeToken<List<String>>() {
                        }.getType());
                    }
                }
                case "optimize_params" -> {
                    if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                        optimize.readJson(jr);
                    }
                }
                default -> jr.skipValue();
            }
        }
        jr.endObject();
    }
}
