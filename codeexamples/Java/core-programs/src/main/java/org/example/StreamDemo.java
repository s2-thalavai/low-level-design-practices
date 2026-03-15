package org.example;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class User {
    String name;
    List<String> emails;

    User(String name, List<String> emails) {
        this.name = name;
        this.emails = emails;
    }
}


class Student {
    String name;
    int[] marks;

    Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
    }
}


class LineItem {
    String product;
    int quantity;
    double price;

    LineItem(String product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}

class Invoice {
    int invoiceId;
    List<LineItem> items;

    Invoice(int invoiceId, List<LineItem> items) {
        this.invoiceId = invoiceId;
        this.items = items;
    }
}

public class StreamDemo {


    public static void main(String[] args) {

         List<Integer> numbers = List.of(1,2,3,4,5);
         numbers.forEach(System.out::println);

        List<String> list = new ArrayList<>();
        list.add("A");

        List<String> unmodifiable =
                Collections.unmodifiableList(list);

        System.out.println("--------");
        unmodifiable.forEach(System.out::println);
        System.out.println("--------");
        list.add("B");
        unmodifiable.forEach(System.out::println);


        List<List<Integer>> list1 = List.of(
                List.of(1,2),
                List.of(3,2),
                List.of(5,1)
        );

        list1.stream()
                .flatMap(Collection::stream)
                .forEach(System.out::println);


        String text = "Java Stream API is powerful";
        List<String> result = null;
        result = Pattern.compile("\\w+").matcher(text).results().map(MatchResult::group).toList();

        List<String> list2 = List.of(
                "test@gmail.com",
                "hello",
                "user@yahoo.com"
        );

        Pattern pattern = Pattern.compile(".*@.*");

        List<String> emails =
                list2.stream()
                        .filter(pattern.asPredicate())
                        .toList();

        System.out.println(emails);


        String text1 = "Order1 price 200 Order2 price 350";

        List<Integer> numbers1 =
                Pattern.compile("\\d+")
                        .matcher(text)
                        .results()
                        .map(m -> Integer.parseInt(m.group()))
                        .toList();

        System.out.println(numbers1);


        String sentence = "Java,Python,C++,Go";

        List<String> languages =
                Pattern.compile(",")
                        .splitAsStream(sentence)
                        .toList();

        System.out.println(languages);

        System.out.println("------------");
          Pattern.compile(",")
                .splitAsStream(sentence)
                        .forEach(System.out::println);

        System.out.println("------------");
          Pattern.compile("[^,]+").matcher(sentence).results().map(res -> res.group()).forEach(System.out::println);

        System.out.println("------------");


        List<String> sentences = List.of(
                "Java Stream API",
                "FlatMap example"
        );

        System.out.println(sentences);

        System.out.println("--------");

        sentences.forEach(System.out::println);
        sentences.stream().flatMap( sen -> Arrays.stream(sen.split(" "))).forEach(System.out::println);

        System.out.println("--------");

        List<Integer> numbers12 = List.of(5,1,4,2, 0, 100);

        numbers12.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------");

        Stream.of(1,2,3,4,5)
                .limit(3)
                .forEach(System.out::println);

        Stream.of(1,2,3,4)
                .skip(2)
                .forEach(System.out::println);

        System.out.println("--------");

        // long count = names.stream().count();

       //  System.out.println(count);

        Optional<Integer> first1 =
                Stream.of(5,6,7).findFirst();

        System.out.println(first1.get().intValue());

        Optional<Integer> any =
                Stream.of(1,2,3).findAny();
        System.out.println(any.get().intValue());


        boolean result12 =
                numbers.stream()
                        .anyMatch(n -> n > 4);
        System.out.println(result12);


        boolean result13 =
                numbers.stream()
                        .allMatch(n -> n > 0);
        System.out.println(result13);

        boolean result14 =
                numbers.stream()
                        .noneMatch(n -> n < 0);
        System.out.println(result14);

        System.out.println("--------");

        int [] numbers18 = {11,16,56,22,19};
        List<Integer> numbersList =
                Arrays.stream(numbers18)
                        .boxed()
                        .toList();

        System.out.println(numbersList);

        System.out.println("--------");

        Optional<Integer> min =
                numbersList
                        .stream()
                        .min(Integer::compare);

        System.out.println(min.get().intValue());

        Optional<Integer> min1 =
                numbersList.stream()
                        .min(Integer::compareTo);
        System.out.println(min1.get().intValue());

        Optional<Integer> max =
                numbersList
                        .stream()
                        .max(Integer::compare);

        System.out.println(max.get().intValue());

        Optional<Integer> max1 =
                numbersList.stream()
                        .max(Integer::compareTo);
        System.out.println(max1.get().intValue());


        Optional<Integer> min12 = numbers.stream().min(Comparator.naturalOrder());
        System.out.println(min12.get().intValue());

        Optional<Integer> max12 = numbers.stream().max(Comparator.naturalOrder());
        System.out.println(max12.get().intValue());

        System.out.println("--------");
        List<Integer> numbers34 = List.of(1,2,3,4);

        numbers34.stream()
                .peek(n -> System.out.println("Processing: " + n))
                .forEach(System.out::println);
        System.out.println("--------");
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("After filter: " + n))
                .map(n -> n * 10)
                .peek(n -> System.out.println("After map: " + n))
                .forEach(System.out::println);

        System.out.println("------count--");

        long count = numbers.stream()
                .peek(System.out::println)
                .count();

        System.out.println("Count: " + count);

        System.out.println("--------" + count);

        List<String> names = List.of("ram","sam","john");

        List<String> resultnames =
                names.stream()
                        .peek(n -> System.out.println("Original: " + n))
                        .map(String::toUpperCase)
                        .peek(n -> System.out.println("Upper: " + n))
                        .toList();
        System.out.println("--------" + resultnames);


        Map<Integer, List<String>> resultByLEngth =
                names.stream()
                        .collect(Collectors.groupingBy(String::length));
        System.out.println("--------" + resultByLEngth);

        Map<Integer, List<String>> resultByLEngth1 =
                names.stream()
                        .collect(Collectors.groupingBy(str -> str.length()));
        System.out.println("--------" + resultByLEngth1);


        System.out.println("--------");


        List<Integer> numbersGr = List.of(1,2,3,4,5,6);

        Map<String, List<Integer>> resultGrp =
                numbersGr.stream()
                        .collect(Collectors.groupingBy(
                                n -> n % 2 == 0 ? "Even" : "Odd"
                        ));

        System.out.println(resultGrp);

        System.out.println("=====================START==============================");

        List<Employee> employees1 = List.of(
                new Employee("Ram","IT"),
                new Employee("Sam","HR"),
                new Employee("John","IT")
        );

        Map<String, List<Employee>> resultMAp =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment()
                        ));

        System.out.println(resultMAp);

        Map<String, List<Employee>> resultTreemap =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                TreeMap::new,
                                Collectors.toList()
                        ));

        System.out.println(resultTreemap);

        System.out.println("-----");

        // {HR=1, IT=2}
        Map<String, Long> countMap =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.counting()
                        ));

        System.out.println("-----" + countMap);

        // {HR=0.0, IT=0.0}

        Map<String, Double> salarySum =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.summingDouble(e -> e.getSalary())
                        ));

        System.out.println("-----" + salarySum);

        // {HR=[Sam], IT=[Ram, John]}
        Map<String, List<String>> namesByDept =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.mapping(e -> e.getName(), Collectors.toList())
                        ));

        System.out.println("-----" + namesByDept);

        // {HR={Low=[Employee{id=0, name='Sam', salary=0.0}]}, IT={Low=[Employee{id=0, name='Ram', salary=0.0}, Employee{id=0, name='John', salary=0.0}]}}
        Map<String, Map<String, List<Employee>>> resultByNeteste =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.groupingBy(
                                        e -> e.getSalary() > 50000 ? "High" : "Low"
                                )
                        ));

        System.out.println("-----" + resultByNeteste);


        Map<String, Employee> resultmap1 =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.collectingAndThen(
                                    Collectors.maxBy(
                                            Comparator.comparingDouble(e -> e.getSalary())
                                    ), Optional::get
                                )
                        ));

        System.out.println("-----" + resultmap1);

        Map<String, Double> result123 =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getDepartment(),
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(
                                                Comparator.comparingDouble(e -> e.getSalary())
                                        ),
                                        emp -> emp.get().getSalary()
                                )
                        ));

        System.out.println("---2nd--");
        // {HR=Optional.empty, IT=Optional[Employee{id=0, name='John', salary=0.0}]}
        Map<String, Optional<Employee>> result2nd =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list1q -> list1q.stream()
                                                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                                .skip(1)
                                                .findFirst()
                                )
                        ));

        System.out.println("-----" + result2nd);

        // {HR=0.0, IT=0.0}
        Map<String, Double> result2nds =
                employees1.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list2q -> list2q.stream()
                                                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                                .skip(1)
                                                .findFirst()
                                                .map(Employee::getSalary)
                                                .orElse(0.0)
                                )
                        ));
        System.out.println("-----" + result2nds);

        System.out.println("========================END===========================");

        List<List<Employee>> employees = List.of(
                List.of(new Employee("Ram"), new Employee("Sam")),
                List.of(new Employee("John"), new Employee("David"))
        );

        List<Employee> result1 =
                employees.stream()
                        .flatMap(Collection::stream)
                        .toList();

        System.out.println(result1);

        List<User> users = List.of(
                new User("Ram", List.of("ram@gmail.com","mam@yahoo.com")),
                new User("Sam", List.of("sam@gmail.com"))
        );


        users.stream().flatMap(user -> user.emails.stream()).forEach(System.out::println);


        Map<String, List<Integer>> map = Map.of(
                "A", List.of(1,2),
                "B", List.of(3,4)
        );

        List<Integer> resultValues = map.values().stream().flatMap(List::stream).toList();
        System.out.println(resultValues);

        List<String> resultKeys = map.keySet().stream().toList();
        System.out.println(resultKeys);


        List<Student> students = List.of(
                new Student("Ram", new int[]{80,90}),
                new Student("Sam", new int[]{70,85})
        );

        System.out.println(students);
        List<Integer> marks = students.stream().flatMap(student -> {
            return Arrays.stream(student.marks).boxed();
        }).toList();

        System.out.println(marks);
        Map<String, Integer> studWithMarks = students.stream()
                .collect(Collectors.toMap(
                        stud -> stud.name,
                        stud -> Arrays.stream(stud.marks).sum()
                ));
        System.out.println(new TreeMap<>(studWithMarks));


        List<Invoice> invoices = List.of(
                new Invoice(1, List.of(
                        new LineItem("Laptop",1,50000),
                        new LineItem("Mouse",2,500)
                )),
                new Invoice(2, List.of(
                        new LineItem("Keyboard",1,1500),
                        new LineItem("Monitor",1,10000),
                        new LineItem("Laptop",1,100000)
                ))
        );

        Map <Integer, Double> invoiceWithTotal = new TreeMap<>();

        invoiceWithTotal =
                invoices.stream()
                        .collect(Collectors.toMap(
                                inv -> inv.invoiceId,
                                inv -> inv.items.stream()
                                        .mapToDouble(i -> i.price * i.quantity)
                                        .sum()
                        ));

        System.out.println(invoiceWithTotal);

        Map<Integer, List<String>> invoiceWithItems =
                invoices.stream()
                        .collect(Collectors.toMap(
                                inv -> inv.invoiceId,
                                inv -> inv.items.stream()
                                        .map(item -> item.product)
                                        .collect(Collectors.toList())
                        ));

        System.out.println(invoiceWithItems);

        List<String> listOfItems = invoiceWithItems.values().stream()
                .flatMap(List::stream)
                .distinct()
                .toList();

        System.out.println(listOfItems);

    }
}
