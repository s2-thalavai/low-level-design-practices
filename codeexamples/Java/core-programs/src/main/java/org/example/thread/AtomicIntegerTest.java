package org.example.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> counter.incrementAndGet());
        Thread t2 = new Thread(() -> counter.incrementAndGet());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter); // 2 Because AtomicInteger uses CAS (Compare-And-Swap).
    }
}