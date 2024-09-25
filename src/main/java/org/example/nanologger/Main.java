package org.example.nanologger;

public class Main {

    public static void main(String[] args)  {
        Logger logger = new Logger(Main.class.getName());
        logger.fatal("Log message {} {}", 1, 2.3);
    }
}