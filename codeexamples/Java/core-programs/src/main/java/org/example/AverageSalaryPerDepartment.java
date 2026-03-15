import java.util.*;
import java.util.stream.Collectors;

public class AverageSalaryPerDepartment {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 90000),
                new Employee("Bob", "IT", 80000),
                new Employee("Sam", "IT", 70000),
                new Employee("Charlie", "HR", 70000),
                new Employee("David", "HR", 75000),
                new Employee("Eva", "Finance", 85000),
                new Employee("Mike", "Finance", 80000)
        );

        Map<String, Double> avgSalary =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.averagingInt(Employee::getSalary)
                        ));

        avgSalary.forEach((dept, avg) ->
                System.out.println(dept + " -> " + avg));
    }
}

class Employee {

    private String name;
    private String department;
    private int salary;

    public Employee(String name, String department, int salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }
}