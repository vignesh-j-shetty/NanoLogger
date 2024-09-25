package org.example.nanologger.Factory;

import org.example.nanologger.LogBuffer;
import org.example.nanologger.RingBuffer.RingLogBuffer;
import org.example.nanologger.config.Configurator;

public class LogBufferFactory {
    public static LogBuffer getLogBuffer() {
        String bufferType = Configurator.getConfig().getBufferType();
        if(bufferType.equals("ringBuffer")){
            return new RingLogBuffer();
        }
        throw new RuntimeException("Invalid buffer given");
    }
}
