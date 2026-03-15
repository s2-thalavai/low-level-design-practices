package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class GroupByDepartmentHighestSalary {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "IT", 90000),
                new Employee(2, "Bob", "IT", 80000),
                new Employee(3,"Charlie", "HR", 70000),
                new Employee(4,"David", "HR", 75000),
                new Employee(5,"Eva", "Finance", 85000)
        );

        Map<String, Employee> result =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparing(Employee::getSalary)),
                                        Optional::get
                                )
                        ));

        result.forEach((dept, emp) ->
                System.out.println(dept + " -> " + emp));


        Map<String, Optional<Employee>> result1 =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> list.stream()
                                                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                                //.skip(1)
                                                .findFirst()
                                )
                        ));

        result1.forEach((dept, emp) ->
                System.out.println(dept + " -> " + emp.orElse(null)));
    }
}

