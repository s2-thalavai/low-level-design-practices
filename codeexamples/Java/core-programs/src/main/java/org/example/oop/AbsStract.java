package org.example.oop;
/*

    Can abstract class have constructor?

        Answer

            Yes

        Constructors run during child object creation.

 */
abstract class AbsStract {

    AbsStract() {

        System.out.println("Constructor");
    }

    abstract void print();
}