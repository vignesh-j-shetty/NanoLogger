package org.example.nanologger.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configurator {
    private static Config config = null;

    public static Config getConfig() {
        if(config == null) {
            config = createConfig();
        }
        return  config;
    }

    private static Config createConfig() {
        String yamlContent;
        try {
            URL res = Configurator.class.getClassLoader().getResource("NanoLogConfig.yaml");
            File yamlConfigFile = Paths.get(res.toURI()).toFile();
            String yamlConfigFilePath = yamlConfigFile.getAbsolutePath();
            yamlContent = Files.readString(Paths.get(yamlConfigFilePath));
        } catch (Exception e) {
            yamlContent = "";
        }
        ObjectMapper mapper = new YAMLMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Config config;
        try {
            config = mapper.readValue(yamlContent, Config.class);
        } catch (IOException e) {
            // pass in default configs
            config = new Config();
        }
        return config;
    }
}
