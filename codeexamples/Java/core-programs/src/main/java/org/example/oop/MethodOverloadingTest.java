package org.example.oop;
/*

    Overload Resolution Order

    Java prefers:

    Exact match
    Widening
    Autoboxing
    Varargs


    Compile-time Binding

    Used for:

    static methods
    private methods
    final methods
    fields

 */
public class MethodOverloadingTest {

    void print(int a) {

        System.out.println("int");
    }

    void print(long a) {

        System.out.println("long");
    }

    public static void main(String[] args) {

        new MethodOverloadingTest().print(10);
    }
}