package org.example.oop;
/*

        Static methods use method hiding, not overriding.

        Resolved at compile time.
 */
class A13 {
    static void print() {
        System.out.println("A");
    }
}

class B13 extends A13 {
    static void print() {
        System.out.println("B");
    }
}

public class MethodHidingTest {

    public static void main(String[] args) {
        A13 obj = new B13();
        obj.print();
    }
}