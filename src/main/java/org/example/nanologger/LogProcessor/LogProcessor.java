package org.example.nanologger.LogProcessor;


import org.example.nanologger.LogEvent;
import org.example.nanologger.LogLevel;
import org.example.nanologger.LogMessageAppenders.LogMessageAppender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogProcessor {
    private final LogLineProcessor logLineProcessor;
    private final LogMessageAppender messageAppender;

    class LogLineProcessor {
        private String logLine;
        private Map<String, String[]> cache;


       LogLineProcessor(String logLine, Map<String, String[]> cache) {
            this.logLine = logLine;
            this.cache = cache;
        }

        LogLineProcessor processLevel(LogLevel logLevel) {
            return new LogLineProcessor(logLine.replace("%level", logLevel.toString()), cache);
        }

        LogLineProcessor processContext(String context) {
            return new LogLineProcessor(logLine.replace("%context", context), cache);
        }

        LogLineProcessor processMessage(String message) {
            return new LogLineProcessor(logLine.replace("%message", message), cache);
        }

        LogLineProcessor processTimeStamp() {
            List<String> timeStampFormats = new ArrayList<>();
            final String timeStampFormatKey = "timeStampFormat";
            if(cache.containsKey(timeStampFormatKey)) {
                timeStampFormats = new ArrayList<>(List.of(cache.get(timeStampFormatKey)));
            } else {
                Pattern pattern = Pattern.compile("%t *\\{[a-zA-Z:\\.\\ -]+}");
                Matcher matcher = pattern.matcher(logLine);
                while(matcher.find()) {
                    timeStampFormats.add(matcher.group());
                }
                String[] formats = new String[timeStampFormats.size()];
                timeStampFormats.toArray(formats);
                cache.put(timeStampFormatKey, formats);
            }

            for(String timeStampFormat : timeStampFormats) {
                int openBracketIndex = timeStampFormat.indexOf('{');
                int closingBracketIndex = timeStampFormat.indexOf('}');
                String format = timeStampFormat.substring(openBracketIndex + 1, closingBracketIndex);
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                String formattedNow = now.format(formatter);
                logLine = logLine.replace(timeStampFormat, formattedNow);
            }

            return new LogLineProcessor(logLine, cache);
        }

        String getLogLine() {
            return logLine;
        }

        private Map<String, String[]> getCache() {
            return cache;
        }
    }

    public LogProcessor(String className, LogMessageAppender logMessageAppender, String pattern) {
        this.logLineProcessor = new LogLineProcessor(pattern, new HashMap<>()).processContext(className);
        this.messageAppender = logMessageAppender;
    }

    public void process(LogEvent event) {
        if (event == null) {
            // Nothing to process
            return;
        }
        StringBuilder messageBuilder = new StringBuilder(event.getMessage());
        for (Object items : event.getObjects()) {
            int index = messageBuilder.indexOf("{}");
            messageBuilder.replace(index, index + 2, items.toString());
        }
        messageBuilder.append('\n');
        process(messageBuilder.toString(), event.getLogLevel());
    }

    public void flushAppender() {
        try {
            messageAppender.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(String message, LogLevel logLevel) {
        String logLine = logLineProcessor
                .processLevel(logLevel)
                .processMessage(message)
                .processTimeStamp()
                .getLogLine();
        try {
            messageAppender.append(logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
