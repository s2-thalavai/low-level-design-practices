package org.example;

import java.util.*;

public class SecondHighest {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10, 20, 30, 40, 40, 30);

        Optional<Integer> secondHighest =
                list.stream()
                        .distinct()
                        .sorted(Comparator.reverseOrder())
                        .skip(1)
                        .findFirst();

        secondHighest.ifPresent(System.out::println);
    }
}