package org.example.nanologger.LogConsumer;

import org.example.nanologger.LogBuffer;
import org.example.nanologger.LogEvent;
import org.example.nanologger.LogProcessor;

public class LogConsumer {

    private final LogBuffer logBuffer;
    private final LogProcessor logProcessor;

    private static class LogConsumerThread extends Thread {
        private final LogBuffer logBuffer;
        private boolean shouldConsume = true;
        private final LogProcessor logProcessor;
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
        }

        public void stopConsuming() {
            shouldConsume = false;
        }
    }

    private LogConsumerThread thread;

    public LogConsumer(LogBuffer logBuffer, LogProcessor logProcessor) {
        this.logBuffer = logBuffer;
        this.logProcessor = logProcessor;
    }

    public void startConsumer() {
        thread = new LogConsumerThread(logBuffer, logProcessor);
        thread.setDaemon(true);
        thread.start();
        // Setup shutdown hooks to flush remaining data
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDownConsumer));
    }

    public void shutDownConsumer() {
        thread.stopConsuming();
        while (!logBuffer.isEmpty()) {
            LogEvent logEvent = logBuffer.get();
            logProcessor.process(logEvent);
        }
    }

}
