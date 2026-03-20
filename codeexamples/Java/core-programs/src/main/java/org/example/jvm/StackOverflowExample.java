package org.example.jvm;
/*

Every method call creates a stack frame.

Example call stack:

    main()
      └─ recurse()
           └─ recurse()
                └─ recurse()
                     └─ recurse()
                          ...

    Each call consumes stack memory.

    Eventually the stack fills up → JVM throws:

    StackOverflowError

    Stack Memory
    -------------------------
    main()
    recurse()
    recurse()
    recurse()
    recurse()
    ....

    Once the stack limit is reached → error occurs.



 */
public class StackOverflowExample {

    static void recurse() {

        recurse();   // infinite recursion
    }

    public static void main(String[] args) {

        recurse();
    }
}