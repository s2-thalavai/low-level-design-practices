package org.example.oop;
/*

    Class A

    ✔ Class wins over interface

 */
class AQZ {

    void print() {

        System.out.println("Class A");
    }
}

interface BQZ {

    default void print() {

        System.out.println("Interface B");
    }
}

public class ClassVsInterfaceOverRideTest extends AQZ implements BQZ {

    @Override
    public void print() {
        BQZ.super.print();
    }
    // No override needed
}