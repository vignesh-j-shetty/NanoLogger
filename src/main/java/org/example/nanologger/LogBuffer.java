package org.example.nanologger;

public interface LogBuffer {
    // If full blocking waits until data is consumed and space is made for write
    void add(LogEvent item);
    // If empty blocking waits until data is available
    LogEvent get();
    // Interrupts all waiting
    void interrupt();
}
