package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class TopKNumbers {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10, 50, 20, 40, 30, 60);

        int k = 3;

        List<Integer> topK =
                list.stream()
                        .sorted(Comparator.reverseOrder())
                        .limit(k)
                        .collect(Collectors.toList());

        System.out.println(topK);
    }
}