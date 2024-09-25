package org.example.nanologger.LogMessageAppenders;

public class LogMessageConsoleAppender implements LogMessageAppender {
    @Override
    public void append(String message) {
        System.out.print(message);
    }

    @Override
    public void flush() {
        // Do nothing
    }
}
