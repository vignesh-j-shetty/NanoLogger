package org.example.nanologger;

public class LogEvent {
    String message;
    Object[] objects;
    LogLevel logLevel;

    public LogEvent(String msg, Object[] objects, LogLevel logLevel) {
        this.message = msg;
        this.objects = objects;
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public Object[] getObjects() {
        return objects;
    }
}
