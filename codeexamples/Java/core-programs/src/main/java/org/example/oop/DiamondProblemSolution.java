package org.example.oop;
/*

    if not override with super, then Compilation Error

    Why

    Ambiguity.

    Must override method.

    Call default method from interface A

    ✔ Explicit conflict resolution
    ✔ Removes ambiguity

 */

interface AI {
    default void print() {

        System.out.println("A");
    }
}

interface BI {
    default void print() {

        System.out.println("B");
    }
}

public class DiamondProblemSolution implements AI, BI {

    @Override
    public void print() {
        AI.super.print(); // explicitly choose A
        BI.super.print(); // explicitly choose B
    }

    public static void main(String[] args) {

    }
}