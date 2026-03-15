package org.example;

import java.util.*;
import java.util.stream.*;

public class BoxedExample {

    public static void main(String[] args) {

        List<Integer> numbers =
                IntStream.range(1,10)
                         .boxed()
                         .toList();

        System.out.println(numbers);
    }
}