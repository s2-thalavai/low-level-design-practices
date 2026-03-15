package org.example;

import java.util.*;

public class LongestStringExample {

    public static void main(String[] args) {

        List<String> list = Arrays.asList(
                "Java",
                "Spring",
                "Microservices",
                "Docker",
                "Kubernetes"
        );

        Optional<String> longest =
                list.stream()
                    .max(Comparator.comparingInt(String::length));

        longest.ifPresent(System.out::println);
    }
}