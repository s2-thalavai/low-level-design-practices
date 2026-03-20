package org.example.thread;

public class WithoutVolatileTest {

    static boolean running = true;

    public static void main(String[] args) {

        new Thread(() -> {

            while(running) {}

            System.out.println("Stopped");

        }).start();

        running = false;
    }
}