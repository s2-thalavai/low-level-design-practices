package org.example.oop;
/*
    10
    Why

    Fields are not polymorphic.

    Resolved at compile time.

*/

class Aa {
    int x = 10;
}

class Ba extends Aa {
    int x = 20;
}

public class ObjectSlicingTest {
    public static void main(String[] args) {

        Aa obj = new Ba();
        System.out.println(obj.x);

    }
}