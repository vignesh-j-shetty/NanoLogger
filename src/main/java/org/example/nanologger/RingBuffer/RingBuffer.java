package org.example.nanologger.RingBuffer;


import org.example.nanologger.LogEvent;

class RingBuffer {

    private final LogEvent[] ringBuffer;
    private final int size;
    private int head = 0;
    private int tail = 0;
    private int count = 0;
    private boolean isWaiting = false;
    private final int waterMark;

    public RingBuffer(int size) {
        ringBuffer = new LogEvent[size];
        this.size = size;
        waterMark = size <= 10 ? 3 : 5;
    }

    public void write(LogEvent item) {
        if((size - count) < waterMark) {
            synchronized (this) {
                add(item);
            }
        } else {
            add(item);
        }

        if(isWaiting) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public synchronized LogEvent read()  {
        LogEvent item;
        if(isEmpty()) {
            try {
                waitForNotify();
            } catch (InterruptedException e) {
                isWaiting = false;
            }
        }
        item = remove();
        return item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Boolean isFull() {
        return count == size;
    }

    private void add(LogEvent item) {
        ringBuffer[head] = item;
        head = (head + 1) % size;
        count++;
        assert count < size;
    }

    private LogEvent remove() {
        LogEvent item = ringBuffer[tail];
        tail = (tail + 1) % size;
        count--;
        assert count >= 0;
        return item;
    }

    private void waitForNotify() throws InterruptedException {
        isWaiting = true;
        wait();
        isWaiting = false;
    }

}
