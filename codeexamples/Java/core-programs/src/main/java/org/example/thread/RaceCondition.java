package org.example.thread;

public class RaceCondition {

    static int counter = 0;

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> counter++);
        Thread t2 = new Thread(() -> counter++);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);  // 2 Threads may overwrite each other.
    }
}