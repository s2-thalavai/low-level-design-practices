package org.example;

import java.util.*;
import java.util.stream.*;

public class ParallelDistinctExample {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            list.add(i % 10000);
        }

        long start = System.currentTimeMillis();

        List<Integer> result =
                list.parallelStream()
                    .distinct()
                    .toList();

        long end = System.currentTimeMillis();

        System.out.println("Unique elements: " + result.size());
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}