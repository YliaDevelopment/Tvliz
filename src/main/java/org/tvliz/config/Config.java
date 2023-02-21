package org.tvliz.config;

import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class Config extends LinkedHashMap<String, Object> {

    private final String configPath;
    private boolean valid;

    public Config(String path) {
        this(path, null);
    }

    public Config(File file) {
        this(file.getAbsolutePath(), null);
    }

    public Config(String path, LinkedHashMap<String, Object> defaults) {
        this.configPath = path;
        this.valid = true;

        if (defaults != null) {
            this.putAll(defaults);
        }

        this.init();
    }

    private void init() {
        if (this.configPath == null ||
                this.configPath.isEmpty()) {

            this.valid = false;
            return;
        }

        try {
            var fileInputStream = new FileInputStream(this.configPath);
            var yaml = new Yaml();

            this.putAll(yaml.load(fileInputStream));
        } catch (Exception e) {
            this.valid = false;
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, Object> getAsMap(String key) {
        //noinspection unchecked
        return (LinkedHashMap<String, Object>) this.get(key);
    }

    public void save() {
        try {
            var file = new File(this.configPath);

            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }

            var yaml = new Yaml();
            var fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(
                    yaml.dumpAs(new LinkedHashMap<>(this), Tag.MAP, FlowStyle.BLOCK)
                            .getBytes(StandardCharsets.UTF_8));

            fileOutputStream.close();
        } catch (Exception ignore) {
        }
    }

    public boolean isValid() {
        return this.valid;
    }
}
