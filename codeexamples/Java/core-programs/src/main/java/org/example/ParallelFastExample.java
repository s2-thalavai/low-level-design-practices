package org.example;

import java.util.stream.IntStream;

public class ParallelFastExample {

    public static void main(String[] args) {

        long start = System.nanoTime();

        long count = IntStream.range(1, 1_000_000)
                              .parallel()
                              .filter(ParallelFastExample::isPrime)
                              .count();

        long end = System.nanoTime();

        System.out.println("Prime count: " + count);
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    public static boolean isPrime(int n) {

        if (n <= 1) return false;

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }

        return true;
    }
}