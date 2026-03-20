package org.example.tricky;

public class TestThreadRun {

    public static void main(String[] args) {

        Thread t = new Thread(() ->
            System.out.println(Thread.currentThread().getName())
        );

        t.run(); // main normal method call
        t.run(); // main normal method call

        t.start(); // main new thread execution

        t.run(); // main
        t.run(); // main
        t.run(); // main
        t.run(); // main
        t.run(); // main
        t.run(); // main
        t.run(); // Thread-0

        // t.start(); // threadillegalstate exception

    }
}