package org.example.nanologger;

public enum LogLevel {
    TRACE, DEBUG, INFO, WARN, ERROR, FATAL;

    public int getLevelValue() {
        switch (this) {
            case TRACE -> {
                return 0;
            }
            case DEBUG -> {
                return 1;
            }
            case INFO -> {
                return 2;
            }
            case WARN -> {
                return 3;
            }
            case ERROR -> {
                return 4;
            }
            case FATAL -> {
                return 5;
            }
        }
        return Integer.MAX_VALUE;
    }
}
