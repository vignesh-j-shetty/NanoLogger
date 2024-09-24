package org.example.nanologger;

public class LogEvent {
    String msg;
    Object[] objects;
    LogLevel logLevel;
    public LogEvent(String msg, Object[] objects, LogLevel logLevel) {
        this.msg = msg;
        this.objects = objects;
        this.logLevel = logLevel;
    }

}
