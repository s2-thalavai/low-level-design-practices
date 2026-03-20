package org.example.oop;
/*

    Private methods cannot be overridden.

 */
class A14 {

    private void print() {

        System.out.println("A");
    }

    void call() {

        print();
    }
}

class B14 extends A14 {

    void print() {

        System.out.println("B");
    }
}

public class PrivateMEthodOverRideTest {

    public static void main(String[] args) {

        A14 obj = new B14();
        obj.call();
    }
}