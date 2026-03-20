package org.example.thread;

public class ThreadLocalTest {

    static ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {

        local.set(10); // main thread

        Thread t = new Thread(() -> {
            System.out.println("Value: " + local.get());  // null Because value is stored only in main thread
        });

        t.start();
        t.join();
    }
}