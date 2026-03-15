package org.example;

import java.util.*;

public class PeekExample {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("Java", "Spring", "Docker");

        list.stream()
            .peek(System.out::println);   // nothing happens

        list.stream()
                .peek(System.out::println)
                .forEach(s -> {});

        List<String> result =
                list.stream()
                        .peek(s -> System.out.println("Original: " + s))
                        .map(String::toUpperCase)
                        .peek(s -> System.out.println("Uppercase: " + s))
                        .toList();

        System.out.println(result);
    }
}