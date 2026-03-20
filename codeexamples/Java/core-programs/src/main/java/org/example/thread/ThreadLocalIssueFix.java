package org.example.thread;

public class ThreadLocalIssueFix {

    static ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {

        local.set(10);

        int value = local.get(); // capture from main thread

        Thread t = new Thread(() -> {
            System.out.println("Value: " + value);
        });

        t.start();
        t.join();
    }
}