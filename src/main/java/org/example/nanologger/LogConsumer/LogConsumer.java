package org.example.nanologger.LogConsumer;

import org.example.nanologger.LogBuffer;
import org.example.nanologger.LogEvent;
import org.example.nanologger.LogProcessor.LogProcessor;

public class LogConsumer {

    private final LogBuffer logBuffer;
    private final LogProcessor logProcessor;

    private static class LogConsumerThread extends Thread {
        private final LogBuffer logBuffer;
        private boolean shouldConsume = true;
        private final LogProcessor logProcessor;
        private volatile boolean isFinished = false;

        public LogConsumerThread(LogBuffer logBuffer, LogProcessor logProcessor) {
            this.logBuffer = logBuffer;
            this.logProcessor = logProcessor;
        }
        @Override
        public void run() {
            shouldConsume = true;
            while(shouldConsume) {
                LogEvent logEvent = logBuffer.get();
                logProcessor.process(logEvent);
            }
            isFinished = true;
            synchronized (this) {
                notifyAll();
            }
        }

        public void stopConsuming() {
            shouldConsume = false;
            logBuffer.interrupt();
            // Wait for consumer function to exit
            synchronized (this) {
                while (!isFinished) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        // Do nothing try to exit.
                    }
                }
            }
        }
    }

    private LogConsumerThread thread;

    public LogConsumer(LogBuffer logBuffer, LogProcessor logProcessor) {
        this.logBuffer = logBuffer;
        this.logProcessor = logProcessor;
    }

    public void startConsumer() {
        // Setup shutdown hooks to flush remaining data
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDownConsumer));
        thread = new LogConsumerThread(logBuffer, logProcessor);
        thread.setDaemon(true);
        thread.start();
    }

    public void shutDownConsumer() {
        thread.stopConsuming();
        while (!logBuffer.isEmpty()) {
            LogEvent logEvent = logBuffer.get();
            logProcessor.process(logEvent);
        }
        logProcessor.flushAppender();
    }
}
