package org.example.nanologger.LogMessageAppenders;

import java.io.IOException;

public interface LogMessageAppender {

    void append(String message) throws IOException;

    void flush() throws  IOException;
}
