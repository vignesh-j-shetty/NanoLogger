package org.example.nanologger.config;

import org.example.nanologger.LogLevel;

public class Config {
    // Default Configs initialized
    private String logOutputType = "console";
    private String logOutputPattern = "%level %t{yyyy-MM-dd HH:mm:ss.SSS}  %context %message";
    private LogFileConfig logFileConfig = new LogFileConfig();
    private String bufferType = "ringBuffer";
    private String logLevel = "TRACE";

    public Config() {
        // Empty Constructor
    }

    public Config(String logOutputType, String logOutputPattern, LogFileConfig logFileConfig, String bufferType, String logLevel) {
        this.logOutputType = logOutputType;
        this.logOutputPattern = logOutputPattern;
        this.logFileConfig = logFileConfig;
        this.bufferType = bufferType;
        this.logLevel = logLevel;
    }

    public String getLogOutputType() {
        return logOutputType;
    }

    public String getLogOutputPattern() {
        return logOutputPattern;
    }

    public LogFileConfig getLogFileConfig() {
        return logFileConfig;
    }

    public String getBufferType() {
        return bufferType;
    }

    public LogLevel getLogLevel() {
        return LogLevel.valueOf(logLevel);
    }
}
