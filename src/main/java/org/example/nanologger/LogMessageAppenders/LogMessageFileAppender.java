package org.example.nanologger.LogMessageAppenders;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogMessageFileAppender implements LogMessageAppender {
    private BufferedWriter bufferedWriter;
    public LogMessageFileAppender(String filepath, Boolean isAppend) {
        try {
            FileWriter fileWriter = new FileWriter(filepath, isAppend);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void append(String message) throws IOException {
        bufferedWriter.write(message);
    }

    @Override
    public void flush() throws IOException {
        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
