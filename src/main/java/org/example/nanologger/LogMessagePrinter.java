package org.example.nanologger;

public class LogMessagePrinter implements LogMessageAppender {
    @Override
    public void append(String message) {
        System.out.println(message);
    }
}
