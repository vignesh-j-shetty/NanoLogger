package org.example.nanologger.RingBuffer;


import org.example.nanologger.LogBuffer;
import org.example.nanologger.LogEvent;

public class RingLogBuffer implements LogBuffer {
    private final RingBuffer ringBuffer;

    public RingLogBuffer() {
        this.ringBuffer = new RingBuffer(100);
    }

    @Override
    public void add(LogEvent item) {
        ringBuffer.write(item);
    }

    @Override
    public LogEvent get() {
        return ringBuffer.read();
    }

    @Override
    public void interrupt() {
        synchronized (ringBuffer) {
            ringBuffer.notifyAll();
        }
    }
}
