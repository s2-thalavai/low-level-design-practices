package org.example;

import java.util.*;
import java.util.stream.*;

public class ParallelSlowExample {

    public static void main(String[] args) {

        List<Integer> list = IntStream.range(0, 1000)
                                      .boxed()
                                      .toList();

        long start = System.currentTimeMillis();

        int sum = list.parallelStream()
                      .mapToInt(i -> i * 2)
                      .sum();

        long end = System.currentTimeMillis();

        System.out.println("Sum: " + sum);
        System.out.println("Time: " + (end - start));
    }
}