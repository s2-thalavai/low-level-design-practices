package org.example.jvm;

public class VolatileDemo {

    static volatile boolean flag = false;

    public static void main(String[] args) {

        new Thread(() -> {
            while (!flag) {}
            System.out.println("Stopped");
        }).start();

        flag = true;
    }
}