package org.example.oop;
/*

    Runtime polymorphism.

    Method resolved at runtime based on object type.

    Runtime Binding

    Used for:

        instance methods
        overridden methods

 */

class A1 {
    void print() {
        System.out.println("A");
    }
}

class B1 extends A1 {
    void print() {
        System.out.println("B");
    }
}

public class MEthodOverridingTest {
    public static void main(String[] args) {
        A1 obj = new B1();
        obj.print();
    }
}