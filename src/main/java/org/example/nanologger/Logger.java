package org.example.nanologger;


import org.example.nanologger.Factory.LogBufferFactory;
import org.example.nanologger.Factory.LogConsumerFactory;
import org.example.nanologger.LogConsumer.LogConsumer;
import org.example.nanologger.config.Configurator;

public class Logger {
    private final LogBuffer logBuffer;
    private final int logLevel;
    public Logger(String className) {
        logBuffer = LogBufferFactory.getLogBuffer();
        LogConsumer logConsumer = LogConsumerFactory.getLogConsumer(logBuffer, className);
        logConsumer.startConsumer();
        logLevel = Configurator.getConfig().getLogLevel().getLevelValue();
    }

    public void trace(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.TRACE);
        log(logEvent);
    }

    public void debug(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.DEBUG);
        log(logEvent);
    }

    public void info(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.INFO);
        log(logEvent);
    }

    public void warn(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.WARN);
        log(logEvent);
    }

    public void error(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.ERROR);
        log(logEvent);
    }

    public void fatal(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.FATAL);
        log(logEvent);
    }


    private void log(LogEvent logEvent) {
        if(logLevel <= logEvent.getLogLevel().getLevelValue()) {
            logBuffer.add(logEvent);
        }
    }

}
