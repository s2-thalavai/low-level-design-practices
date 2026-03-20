package org.example.oop;
/*

        When creating a child object:

        Parent constructor → Child constructor

 */
class A {
    A() {
        System.out.println("A");
    }
}

class B extends A {
    B() {
        System.out.println("B");
    }
}

public class ConstructorTest {
    public static void main(String[] args) {
        new B();
    }
}