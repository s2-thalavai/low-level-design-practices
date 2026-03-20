package org.example.thread;

import java.util.concurrent.*;

public class ThreadLocalExecutorServiceTest {

    static ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {

        ExecutorService pool = Executors.newFixedThreadPool(1);

        local.set(10);

        pool.submit(() -> {
            System.out.println("Value: " + local.get());
        });

        pool.shutdown();
    }
}