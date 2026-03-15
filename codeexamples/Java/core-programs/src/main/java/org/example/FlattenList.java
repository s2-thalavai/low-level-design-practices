package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class FlattenList {

    public static void main(String[] args) {

        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8)
        );

        List<Integer> flatList =
                listOfLists.stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        System.out.println(flatList);
    }
}