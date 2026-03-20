package org.example.oop;
/*

    This is overloading, not overriding.

    Method selected at compile time.

 */
class A12 {
    void print(int x) {
        System.out.println("A");
    }
}

class B12 extends A12 {
    void print(long x) {
        System.out.println("B");
    }
}

public class OVerLoadVsOverRideTest {
    public static void main(String[] args) {
        A12 obj = new B12();
        obj.print(10);
    }
}