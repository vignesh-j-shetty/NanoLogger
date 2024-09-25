package org.example.nanologger;

public class Main {

    public static void main(String[] args)  {
        Logger logger = new Logger(Main.class.getName());
        long startTime = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            logger.info("hello {}", i);
        }
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }
}