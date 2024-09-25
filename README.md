# NanoLogger

#### _High performance logging library. With very low latency and configurable features._

# Design

_Here Consumer-Producer technique is used here to decouple logging function that is called by applications or users.
Producer runs on same thread as of application/user. Consumer run on its own thread. Consumer and Producer interact through lock-free ring buffer data structure.
Consumer blocking reads (blocked when buffer is empty) from buffer and Producer writes to buffers without any lock contention._


# Usage
        Logger logger = new Logger(Main.class.getName());
        logger.fatal("Log message {} {}", 1, 2.3);
        logger.warn("Warning {}", 1);

For sample config refer resources folder.


# Performance

* Apache log4j Average latency: 1798750 ns
* NanoLogger Average latency: 15709 ns

_(Note that these performance measures are specific to machine used to test)_