package org.example.thread;

public class RaceConditionFix {

    static int counter = 0;

    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> increment());
        Thread t2 = new Thread(() -> increment());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter); // 2 synchronized ensures mutual exclusion.
    }
}