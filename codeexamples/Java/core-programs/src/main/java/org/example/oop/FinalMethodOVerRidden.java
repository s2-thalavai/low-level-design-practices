package org.example.oop;
/*

    Compilation Error

        Final methods cannot be overridden.

 */

class A15 {

    // final void print() {
    void print() {

        System.out.println("A");
    }
}

class B15 extends A15 {

    void print() {

        System.out.println("B");
    }
}

public class FinalMethodOVerRidden {

    public static void main(String[] a) {

    }
}