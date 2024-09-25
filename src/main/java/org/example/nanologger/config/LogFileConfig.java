package org.example.nanologger.config;

public class LogFileConfig {
    private String name = "log.txt";
    private Boolean isAppend = true;

    public String getName() {
        return name;
    }

    public Boolean isAppend() {
        return isAppend;
    }
}
