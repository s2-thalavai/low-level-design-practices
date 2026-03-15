package org.example;

import java.util.*;
import java.util.stream.*;

public class DistinctExample {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        // create large dataset with duplicates
        for (int i = 0; i < 1_000_000; i++) {
            list.add(i % 10000);
        }

        long start = System.currentTimeMillis();

        List<Integer> result =
                list.stream()
                    .distinct()
                    .toList();

        long end = System.currentTimeMillis();

        System.out.println("Unique elements: " + result.size());
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}