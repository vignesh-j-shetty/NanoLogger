package org.example.nanologger;



public class Main {

    public static void main(String[] args)  {
        Logger logger = new Logger(Main.class.getName());
        long startTime = System.nanoTime();
        logger.info("Hello {}", 1);
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }
}