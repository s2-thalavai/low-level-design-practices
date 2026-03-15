package org.example;

import java.util.*;
import java.util.stream.*;


public class Top3SalaryPerDepartment {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 90000),
                new Employee("Bob", "IT", 80000),
                new Employee("Sam", "IT", 70000),
                new Employee("Tom", "IT", 95000),
                new Employee("Charlie", "HR", 70000),
                new Employee("David", "HR", 75000),
                new Employee("John", "HR", 72000),
                new Employee("Eva", "Finance", 85000),
                new Employee("Mike", "Finance", 80000),
                new Employee("Sara", "Finance", 78000)
        );

        Map<String, List<Employee>> result =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> list.stream()
                                                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                                .limit(3)
                                                .collect(Collectors.toList())
                                )
                        ));

        result.forEach((dept, empList) -> {
            System.out.println(dept + " -> " + empList);
        });
    }
}