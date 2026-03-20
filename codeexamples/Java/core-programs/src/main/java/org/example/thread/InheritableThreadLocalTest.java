package org.example.thread;

public class InheritableThreadLocalTest {

    static InheritableThreadLocal<Integer> local = new InheritableThreadLocal<>();

    public static void main(String[] args) throws Exception {

        local.set(10);

        Thread t = new Thread(() -> {
            System.out.println("Value: " + local.get());
        });

        t.start();
        t.join();
    }
}