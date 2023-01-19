package org.localmc.tools.hardcodepatcher.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.localmc.tools.hardcodepatcher.HardcodeTextPatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HardcodeTextPatcherConfig {
    private static final Gson GSON = new Gson();
    private static final Path configFile = HardcodeTextPatcher.configPath.resolve("config.json");
    private static List<String> mods = new ArrayList<>();

    public static List<String> getMods() {
        return mods;
    }

    public static void readConfig() throws IOException {
        JsonReader jr = GSON.newJsonReader(Files.newBufferedReader(configFile));
        jr.beginObject();
        if (Objects.equals(jr.nextName(), "mods") && jr.peek() == JsonToken.BEGIN_ARRAY) {
            mods = GSON.fromJson(jr, new TypeToken<List<String>>(){}.getType());
        } else {
            jr.close();
        }
    }
}