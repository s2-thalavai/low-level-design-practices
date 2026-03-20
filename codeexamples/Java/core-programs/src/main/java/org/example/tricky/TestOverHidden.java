package org.example.tricky;

/*
    Static methods are not overridden, they are method hidden.

    Resolved at compile time.

 */
class A23 {

    static void print() {
        System.out.println("A");
    }
}

class B23 extends A23 {

    static void print() {
        System.out.println("B");
    }
}

public class TestOverHidden {

    public static void main(String[] args) {

        A23 obj = new B23();
        obj.print();
    }
}