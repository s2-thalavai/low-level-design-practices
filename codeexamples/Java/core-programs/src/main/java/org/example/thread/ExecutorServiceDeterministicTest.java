package org.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDeterministicTest {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(1);

        executor.submit(() -> System.out.print("A"));
        executor.submit(() -> System.out.print("B"));

        executor.shutdown();
    }
}