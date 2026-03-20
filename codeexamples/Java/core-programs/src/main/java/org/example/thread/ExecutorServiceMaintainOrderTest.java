package org.example.thread;

import java.util.concurrent.*;

public class ExecutorServiceMaintainOrderTest {

    public static void main(String[] args) throws Exception {

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        Future<?> f1 = executor.submit(() -> System.out.print("A"));
        f1.get(); // wait for A to Force Order

        Future<?> f2 = executor.submit(() -> System.out.print("B"));
        f2.get(); // wait for B to Force Order

        executor.shutdown();
    }
}