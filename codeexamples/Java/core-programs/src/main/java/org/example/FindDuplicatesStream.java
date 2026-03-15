package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FindDuplicatesStream {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 3, 2, 7, 8, 5, 9);

        // Set to track seen elements
        Set<Integer> seen = new HashSet<>();

        // Stream to find duplicates
        Set<Integer> duplicates =
                list.stream()
                        .filter(i -> !seen.add(i))
                        .collect(Collectors.toSet());

        System.out.println("Original List: " + list);
        System.out.println("Duplicate Elements: " + duplicates);


        // thread safe parallel Stream
        Set<Integer> seen1 = Collections.synchronizedSet(new HashSet<>());

        Set<Integer> duplicates1 =
                list.parallelStream()
                        .filter(i -> !seen1.add(i))
                        .collect(Collectors.toSet());

        System.out.println("Duplicates: " + duplicates1);


        // Thread-safe set
        Set<Integer> seen2 = ConcurrentHashMap.newKeySet();

        Set<Integer> duplicates2 =
                list.parallelStream()
                        .filter(i -> !seen2.add(i))
                        .collect(Collectors.toSet());

        System.out.println("Original List: " + list);
        System.out.println("Duplicates: " + duplicates2);


    }
}