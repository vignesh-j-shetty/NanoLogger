package org.example.nanologger;


import org.example.nanologger.LogConsumer.LogConsumer;
import org.example.nanologger.RingBuffer.RingLogBuffer;

public class Logger {
    private final LogBuffer logBuffer;

    public Logger(String className) {
        logBuffer = new RingLogBuffer();
        LogConsumer logConsumer = new LogConsumer(logBuffer, new LogProcessor(className, new LogMessagePrinter()));
        logConsumer.startConsumer();
    }
    public void info(String msg, Object ... obj) {
        LogEvent logEvent = new LogEvent(msg, obj, LogLevel.INFO);
        logBuffer.add(logEvent);
    }

}
