package org.example.tricky;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

class TestTest {

    void print(Integer i) {
        System.out.println("Integer method overloading");
    }
    void print(String s) {
        System.out.println("String method overloading");
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

    public static void change(int arr[]) {
        arr[0] = 10;
    }

    static int sCount = 0;
    static volatile int vCount = 0;


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

    public static void main(String[] args) throws ClassNotFoundException {

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

        System.out.println("============StringBuilder=================");
        // StringBuilder creates heap string, not pooled.
        String s2111 = new StringBuilder()
                .append("Ja")
                .append("va")
                .toString();

        System.out.println(s2111 == s22);

        System.out.println("============String Final=================");
        String s71 = "Java";
        String s72 = "Developer";

        String s73 = s71 + s72;
        String s74 = "JavaDeveloper";

        System.out.println(s73 == s74);

        // Because final variables allow compile-time constant folding.
        final String s81 = "Java";
        final String s82 = "Developer";

        String s83 = s81 + s82;
        String s84 = "JavaDeveloper";

        System.out.println(s83 == s84);

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


        System.out.println("==========When does a static block execute===============");
        /*
        The static block executes when the class is initialized by the JVM.

            This happens when the class is first actively used.

            Examples of triggers:

                Creating an object

                Accessing a static variable

                Calling a static method

                Using Class.forName()
         */

        // Class.forName("Test"); // Static block

        /*
              Reason

                Class.forName() does two things:

                    Loads the class

                    Initializes the class

                    Initialization triggers execution of static blocks.

                Execution flow:

                        Load class
                        ↓
                        Initialize class
                        ↓
                        Run static blocks
         */


        // ClassLoader.getSystemClassLoader().loadClass("Test"); // No output

        System.out.println("==========Thread===============");

        Thread t = new Thread();
        t.start();
       // t.start(); RunTimeException illegalThreadstateException

        /*

        Reason

                A thread can be started only once.

                Thread lifecycle:

                NEW → RUNNABLE → RUNNING -> Waiting / TimedWaiting → TERMINATED

                After a thread starts, calling start() again is illegal.
         */

        vCount++; // Is this thread-safe? // No

        // count++ is not an atomic operation.

        /*

        It actually performs three steps:

            1. read count
            2. increment
            3. write count

            Example problem:

            Thread 1 reads count = 5
            Thread 2 reads count = 5
            Thread 1 writes 6
            Thread 2 writes 6

            Result → lost update

            volatile ensures:

                Feature	        Provided
                Visibility	        ✔
                Atomicity	        ❌

             So all threads see the latest value, but operations like ++ are still unsafe.


         */


        AtomicInteger count = new AtomicInteger(0);
        count.incrementAndGet();

        //synchronized(this) {
        //    count++;
        //}

        // Since the exception is caught, the program continues normally.
        try {
            int x1 = 10/0;  // throws ArithmeticException
        } catch(Exception e) {   // → handles it
        } finally {
            System.out.println("Finally");
        }

        // RuntimeException

        int age = 15;
        //if(age >= 18) {
        //    throw new RuntimeException("Not allowed");
        //}


        char cc = 'A'; // char internally stores Unicode numeric values. 65
        cc++; // 65 + 1

        System.out.println(cc); // 66 = B

        // In Java, arithmetic operations on byte, short, or char automatically promote to int.
        // byte / short / char → promoted to int
        byte by = 10;
       //  by = by + 1;   // Compilation Error possible lossy conversion from int to byte

        by = (byte)(by + 1); // (byte) int
        System.out.println(by);

        by += 1;
        System.out.println(by); // Compound assignment operators perform implicit casting.

        // reference to print is ambiguous

        TestTest tt = new TestTest();
        //  tt.print(null); // Compilation Error reference to print is ambiguous
        tt.print((Integer) null);


        System.out.println("********Array Covariance Trap********");

        Object[] arrCoVarient = new String[3];
        arrCoVarient[0] = "Hello";
       // arrCoVarient[1] = 10; // ArrayStoreException Runtime type of array = String[].


        System.out.println("********StringBuilder********");
        StringBuilder sb = new StringBuilder("Java");
        System.out.println(sb.equals("Java")); // false
        System.out.println(sb.toString().equals("Java")); // true

        /*

        StringBuilder does not override equals().

                So it inherits the default implementation from Object.

                Default Object.equals() behaves like:

            reference comparison

            Equivalent to:

                s1 == s2
        */


        List<Integer> list = Arrays.asList(1,2,3);
       // list.add(4);// Result UnsupportedOperationException
        list.set(1, 4);


        /*

                J6 substring() did NOT create a new char array.

                Instead, it shared the same underlying char[].

                Internal representation (simplified)
                class String {
                    char[] value;
                    int offset;
                    int count;
                }

                So:

                "HelloWorld"
                   ↓
                [H e l l o W o r l d]

                sub = s.substring(0,5):

                sub → same char[] with offset=0, count=5

                String safe = new String(sub);
         */

        /*

                From Java 7 onwards:

                substring() creates a NEW char[]

                So:

                String sub = s.substring(0,5);

                Now:

                sub → new char[] = [H e l l o]

                Original string → separate char[]
                Substring → new char[]

                No memory retention issue.

                Java 6
                [H e l l o W o r l d]
                 ↑───────────────↑
                   shared by both

                Java 7+
                Original → [H e l l o W o r l d]
                Substring → [H e l l o]

         */
        String sH = "HelloWorld";
        String sub = sH.substring(0, 5);
        System.out.println(sub);

        /*

                Even though small is just "Hello":

                small still references the FULL 10MB char[]

                So:

                    10MB memory is retained unnecessarily

                This caused hidden memory leaks.
         */

        String big = new String(new char[10_000_000]);
        String small = big.substring(0, 5);
        System.out.println(small);


        /*
            String in Java is immutable.

            That means:

            Once a String is created, it cannot be changed.

            String Pool / Heap
            ----------------------
            "Java"   ← s
            "Lava"   (unused, eligible for GC)

            String is immutable → all modification methods return new objects.


         */
        String s11a = "Java";
        s11a.replace("J", "L");
        System.out.println(s11a);

        System.out.println(Double.NaN == Double.NaN);  // false IEEE floating-point rule: NaN != NaN

        Double xd = 11.25;
        System.out.println(Double.isNaN(xd));


        System.out.println(1.0 / 0);   // Infinity
        System.out.println(-1.0 / 0);  // -Infinity
        System.out.println(0.0 / 0);   // NaN

        double xqa = 1 / 0;
        System.out.println(xqa/0); // 1 / 0 → integer division happens first Exception in thread "main" java.lang.ArithmeticException: / by zero
        System.out.println(1/0); // Exception in thread "main" java.lang.ArithmeticException: / by zero

        // Integer division → strict → exception
        // Floating-point → IEEE rules → Infinity / NaN

        Test t11 = new Test();
        Test t21 = t11;
        t11 = null; // not eligible // Because t2 still references it.


        byte[] arrBy = new byte[100_000_000]; // This allocates ~100 MB array.

        // Does It Go to Young or Old Generation?

        // Does It Go to Young or Old Generation?

        /*

            1. Classic Generational GC (Parallel / CMS)

            Normally:

                New objects → Young Generation (Eden)

            But for large objects:

            They may be allocated directly in Old Generation

            This is controlled by:

                -XX:PretenureSizeThreshold

            Example:

                -XX:PretenureSizeThreshold=10m

            Objects larger than 10MB go directly to Old Gen.


            2. G1 GC (Modern JVM Default)

                G1 works differently.

                Heap is divided into regions.

                Large objects are called:

                    Humongous Objects

            G1 Rule

                Object > 50% of region size → Humongous

            Example:

                If region = 2MB:

                >1MB → Humongous object

            Your array (100MB):

                Definitely humongous

            Where It Goes

                Allocated directly in Old Generation regions

            (No Young Gen)

            Heap
            --------------------------------
            Young Gen (Eden) → skipped

            Old Gen (Regions)
            --------------------------------
            [Humongous object → byte[100MB]]

            Why JVM Does This

            To avoid:

                     Expensive copying during GC (Young → Old)

                     Large objects are costly to move.

            So JVM places them directly in Old Gen.

            ---------

            Small objects → Young Gen
            Large objects → may go directly to Old Gen

            ---------

            Potential Problem

            Frequent large allocations can cause:

            GC pressure
            Fragmentation
            Full GC pauses

            ---------

            // Class Identity = ClassLoader + Fully Qualified Class Name

            ClassLoader loader1 = new Test1();
            ClassLoader loader2 = new Test1();

            Class<?> c1 = loader1.loadClass("com.example.Test");
            Class<?> c2 = loader2.loadClass("com.example.Test");

            System.out.println(c1 == c2);

            -----------

            Object obj = loader1.loadClass("Test").newInstance();
            System.out.println(obj instanceof Test);

            Test loaded by:

                AppClassLoader

            But object created using:

                CustomClassLoader

            Different class identity → instanceof fails.

            ---------

            Same class name + same bytecode ≠ same class

            Same class = same ClassLoader + same ClassName



         */


    }



}




































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































