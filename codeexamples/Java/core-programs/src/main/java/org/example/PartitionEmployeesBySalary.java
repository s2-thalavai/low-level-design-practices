package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class PartitionEmployeesBySalary {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 90000),
                new Employee("Bob", "IT", 80000),
                new Employee("Charlie", "HR", 70000),
                new Employee("David", "HR", 75000),
                new Employee("Eva", "Finance", 85000)
        );

        // Step 1: Calculate average salary
        double avgSalary =
                employees.stream()
                        .collect(Collectors.averagingDouble(Employee::getSalary));

        // Step 2: Partition employees
        Map<Boolean, List<Employee>> result =
                employees.stream()
                        .collect(Collectors.partitioningBy(
                                e -> e.getSalary() > avgSalary
                        ));

        System.out.println("Above Average Salary:");
        result.get(true).forEach(System.out::println);

        System.out.println("\nBelow or Equal Average Salary:");
        result.get(false).forEach(System.out::println);
    }
}
