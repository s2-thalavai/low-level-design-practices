package org.example.tricky;

import java.math.BigDecimal;
import java.util.HashMap;

interface A {

    void printMethod();

    default void printDefaultMEthod() {
        System.out.println("Hello from Default method");
    }

    static void printStaticMEthod() {
        System.out.println("Hello from Static method");
    }
}


class Abstract1123 implements A {

    @Override
    public void printMethod() {
        System.out.println("Hello from child class method");
    }
}

class Test {

    static int x = 10;

    static {
        x = 20;
    }

    int id;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Test)) return false;
        Test t = (Test)o;
        return this.id == t.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

class MethodOverloadingTest {

    static void print(int x) {
        System.out.println("int static method overloading");
    }

    static void print(long x) {
        System.out.println("long static method overloading");
    }

    static void print(Integer x) {
        System.out.println("Integer static method overloading");
    }

}


class Parent {
    static void show() {
        System.out.println("Parent static show()");
    }

    void show1() {
        System.out.println("Parent instance show1()");
    }
}

class Child extends Parent {
    static void show() {
        System.out.println("Child show()");
    }

    void show1() {
        System.out.println("Child instance show1()");
    }
}

public class TrickyTest {

    public static int test() {
        try {
            throw new RuntimeException();
            // return 1;
        } finally {
            System.out.println("finally executed");
            return 2;
        }
    }

    static void print(int a) {
        System.out.println("int static method overloaded");
    }

    static void print(Integer a) {
        System.out.println("Integer static method overloaded");
    }

    static void print1(Integer a) {
        System.out.println("Integer static method overloaded");
    }

    private static void print2(long test1) {
        System.out.println("long static method overloaded");
    }

    static int x = 10;

    static {
        x = 20;
    }

    public static void main(String[] args) {

        System.out.println("=============String================");

        String s = "Hello";
        s.concat(" World");
        System.out.println(s); //Hello // Strings are immutable concat() returns new string, but result not assigned.

        s = s.concat(" World");
        System.out.println(s); //Hello World

        System.out.println("=============String================");

        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");

        System.out.println(s1 == s2); // true Ref Comparision
        System.out.println(s1 == s3); // false Ref Comparision
        System.out.println(s1.equals(s3)); // true Value Comparision
        System.out.println(s1.equals(s2)); // true Value Comparision

        System.out.println("\nIdentity Hash Codes (similar to address)");

        System.out.println("s1 : " + System.identityHashCode(s1));
        System.out.println("s2 : " + System.identityHashCode(s2));
        System.out.println("s3 : " + System.identityHashCode(s3));

        System.out.println("=============s1 == s3.intern()================");

        System.out.println(s1 == s3.intern());


        System.out.println("============\"Ja\" + \"va\"=================");

        String s11 = "Ja" + "va"; // compile-time constant folding
        String s21 = "Java";

        System.out.println(s11 == s21); // true

        System.out.println("============\"Ja\" + \"va\"=================");

        String a = "Ja";
        String b = "va";

        String s12 = a + b;
        String s22 = "Java";

        System.out.println(s12 == s22);

        System.out.println("============\"Ja\" + \"va\".intern=================");

        String a1 = "Ja";
        String b1 = "va";

        String s111 = (a + b).intern();
        String s211 = "Java";

        System.out.println(s11 == s211);

        System.out.println("=============================");

/*

| Case                | Result                  |
| ------------------- | ----------------------- |
| Literal + Literal   | Compile-time → Pool     |
| Variable + Variable | Runtime → Heap          |
| `intern()`          | Moves reference to pool |

 */

    System.out.println("Integer Cache Trap");
// Java caches integers -128 to 127

    Integer a123 = 127;
    Integer b123 = 127;

    Integer c = 128;
    Integer d = 128;

    System.out.println(a123 == b123); // same cached object
    System.out.println(c == d); // new objects
        System.out.println(c == 128); // primitive comparision causes auto-unboxing c.intValue() == 128

        /* The cache range can be extended using JVM option:
        -XX:AutoBoxCacheMax=1000

        Then Java will cache:

        -128 to 1000

         */
    System.out.println("=========Finally====================");

        System.out.println(test()); // 2 finally overrides return from try

        System.out.println("==============Method Overloading===============");

        TrickyTest.print(5); // Primitive exact match wins over autoboxing.

        int test1 = 10;
        TrickyTest.print1(test1); // int → Integer  (autoboxing)

        // Primitive Widening vs Autoboxing
        TrickyTest.print2(test1); // int → long  (Primitive Widening)

          byte b1234 = 10;
        MethodOverloadingTest.print(b1234); // byte → int (primitive widening)

        MethodOverloadingTest mot = null; // Static methods are resolved at compile time, not runtime.
        mot.print(b1234);  // int static method overloading

        System.out.println("==============Method Overloading===============");

        // The compiler resolves the method using the reference type, not the object type.
        Parent obj = new Child();
        obj.show(); // Parent show() This happens during compilation, not runtime.

        /*
        Reference type → Parent

        So the compiler binds:

        Parent.show()

         */

        obj.show1(); // Child instance show1()  Here Java performs runtime polymorphism.

        /*

        | Method Type          | Binding Time | Behavior                         |
| -------------------- | ------------ | -------------------------------- |
| **Static methods**   | Compile time | Method hiding                    |
| **Instance methods** | Runtime      | Method overriding (polymorphism) |

         */


        String day = "MON";

        switch (day) {
            case "MON":
                System.out.println("Monday");
                break;

            case "TUE":
                System.out.println("Tuesday");
                break;

            default:
                System.out.println("Invalid day");
        }

        // The compiler converts it roughly into something like:

        /*

        String day = "MON";

            int hash = day.hashCode();

            switch(hash) {
                case 77124:
                    if(day.equals("MON")) {
                        System.out.println("Monday");
                    }
                    break;

                case 83500:
                    if(day.equals("TUE")) {
                        System.out.println("Tuesday");
                    }
                    break;
            }

            Why?

            1️. hashCode() → fast lookup
            2️. equals() → resolves hash collisions

         */

        String s1q = null;

        s1q = "as";

        // s1q = null;

        switch (s1q) {
            case "A": System.out.println("A");
        }

        // NPE

        // Case labels must be compile-time constants

        String x = "JAVA";

        switch (s1q) {
            // case x: System.out.println("A");
        }

        // Case is case-sensitive

        s= "JavA";

        switch(s) {
            case "JAVA":
                System.out.println("JAVA Match");
            case "java":
                System.out.println("java Match");
            case "Java":
                System.out.println("Java Match");
            default:
                System.out.println("No Match for " + s);
        }

        // Switch Expression
        day = "MON";
        String result = switch(day) {
            case "MON" -> "Monday";
            case "TUE" -> "Tuesday";
            default -> "Invalid";
        };
        System.out.println("Switch Expression : " + result);


        int num = 2;

        String result1 = switch(num) {
            case 1 -> "One";

            case 2 -> {
                System.out.println("Switch Expression : Processing 2 with ywild");
                yield "Two"; // yield only works inside switch expression blocks
            }

            default -> "Unknown";
        };

        System.out.println(result);

        System.out.println("=====================Array Reference Trap=====================");

        int arr[] = {1,2,3};

        change(arr);

        System.out.println(arr[0]); // 10 Java is pass-by-value, but value is reference copy

        /*

        Stack                        Heap
        -----                        -----
        arr (main) -----------→     [1,2,3]
        arr (change method) ----→   same object

         */

        // Increment Operator Trap
        int i = 5;
        i = i++ + ++i;
        System.out.println(i); // 5 + 7 = 12

        i = 5;
        i = i-- + --i;
        System.out.println(i); // 5 + 3 = 8


        // HashMap Null Key Trick
        // HashMap allows only one null key.
        HashMap<String,String> map = new HashMap<>();
        map.put(null, "A");
        map.put(null, "B");   // replaces previous value

        map.put("X", null);
        map.put("Y", null);
        System.out.println(map.size()); // 1 {null=B, X=null, Y=null}
        // The second put(null, "B") overwrites the first value "A".

        System.out.println("=====================Abnormal termination=====================");

        try {
            System.out.println("Normal and Abnormal Termination");
            //  System.exit(0); // Runtime.getRuntime().exit()
            // Normal termination
           //  This tells the JVM to terminate the entire process, not just the method.

           // System.exit(1); // Abnormal termination

        } finally {
            System.out.println("Finally");
        }

        System.out.println("=====================Finally Not Executed Case=====================");

        // System.exit() terminates JVM immediately.

        /*
            try block starts
               ↓
            System.exit(0)
               ↓
            JVM shutdown
               ↓
            finally block skipped
         */

        // Static Initialization Order
        System.out.println("=====================Static Initialization Order=====================");

        System.out.println("X : " + TrickyTest.x); // Static blocks run during class loading.

        /*

        When a class is loaded by the JVM, static members are initialized in the order they appear in the class.

        Execution order during class loading:

         1. Static variables
         2. Static blocks
         3. main() method executes

            Class Loading
                 ↓
            static int x = 10
                 ↓
            static block → x = 20
                 ↓
            main()
                 ↓
            print x → 20

         */

       // Static variables and static blocks execute in the order they appear in the class during class loading.

        /*

        static {
            x = 20;
        }

        static int x = 10;

        public static void main(String[] args) {
            System.out.println(x);            // 10 // order also matters
        }

            Execution order:

            static block → x = 20
            static int x = 10

            Final value becomes 10.

         */

        System.out.println("=====================final variable can be assigned only once=====================");

        // final int a;

        // System.out.println(a); // Compilation Error. variable a might not have been initialized

        final int af = 10;

        System.out.println(af); // 10

        final int af1;

        af1 = 5;

        System.out.println(af1);   // 5 This works because a is assigned exactly once before being used.

        // Because the compiler can guarantee that a will be initialized.
        final int af2;

        if (true) {
            af2 = 10;
        }

        System.out.println(af2);

        final int af3;

        if (Math.random() > 0.5) {
            af3 = 10;
        }

        /*
            System.out.println(af3);
            Compilation error

            variable a might not have been initialized

            Because the compiler cannot guarantee initialization.

         */

        System.out.println("===========Floating / double Point (Approximate Precision) Trap==============");

        System.out.println(0.1 + 0.2 == 0.3); // false Floating-point precision issue.

        /*

        0.1 ≈ 0.10000000000000000555
        0.2 ≈ 0.20000000000000001110
        When added:

        0.1 + 0.2 ≈ 0.3000000000000000444

        But:

        0.3 ≈ 0.2999999999999999889

        So the comparison becomes:

        0.3000000000000000444 == 0.2999999999999999889

        Which is false.

         */

        double epsilon = 1e-9;

        System.out.println(Math.abs((0.1 + 0.2) - 0.3) < epsilon);

        System.out.println("===========Use BigDecimal (Exact decimal precision) ==============");

        // Best Solution for Money / Precision
        // Use BigDecimal.
        BigDecimal abd = new BigDecimal("0.1");
        BigDecimal bbd = new BigDecimal("0.2");

        System.out.println(abd.add(bbd).equals(new BigDecimal("0.3")));

        System.out.println("=========================");

        HashMap<Test,String> map1 = new HashMap<>();

        Test t1 = new Test();
        Test t2 = new Test();

        map1.put(t1,"A");
        map1.put(t2,"B");

       map1.keySet().forEach(System.out::println);


        System.out.println("==========Interface===============");
        A.printStaticMEthod();

        Abstract1123 aObj = new Abstract1123();
        aObj.printDefaultMEthod();
        aObj.printMethod();

    }

    public static void change(int arr[]) {
        arr[0] = 10;
    }

}




































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































