package org.example.nanologger.Factory;

import org.example.nanologger.LogBuffer;
import org.example.nanologger.LogConsumer.LogConsumer;
import org.example.nanologger.LogMessageAppenders.LogMessageAppender;
import org.example.nanologger.LogMessageAppenders.LogMessageConsoleAppender;
import org.example.nanologger.LogMessageAppenders.LogMessageFileAppender;
import org.example.nanologger.LogProcessor.LogProcessor;
import org.example.nanologger.config.Configurator;
import org.example.nanologger.config.LogFileConfig;

public class LogConsumerFactory {

    public static LogConsumer getLogConsumer(LogBuffer logBuffer, String className) {
        LogMessageAppender logMessageAppender;
        String logOutputType = Configurator.getConfig().getLogOutputType();
        if(logOutputType.equals("console")) {
            logMessageAppender = new LogMessageConsoleAppender();
        } else if(logOutputType.equals("file")) {
            LogFileConfig logFileConfig = Configurator.getConfig().getLogFileConfig();
            logMessageAppender = new LogMessageFileAppender(logFileConfig.getName(), logFileConfig.isAppend());
        } else {
            throw new RuntimeException("Unsupported log output configured");
        }
        return new LogConsumer(logBuffer, new LogProcessor(className, logMessageAppender, Configurator.getConfig().getLogOutputPattern()));
    }
}
