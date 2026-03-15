package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class ListToMapExample {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(1, "Alice"),
                new Employee(2, "Bob"),
                new Employee(2, "David"),
                new Employee(3, "Charlie")
        );

        Map<Integer, String> map =
                employees.stream()
                        .collect(Collectors.toMap(
                                Employee::getId,
                                Employee::getName,
                                (oldValue, newValue) -> oldValue + ", " + newValue
                        ));

        System.out.println(map);
    }
}