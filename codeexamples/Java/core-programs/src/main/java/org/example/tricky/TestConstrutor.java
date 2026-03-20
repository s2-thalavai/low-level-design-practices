package org.example.tricky;

class A12 {

    A12() {
        System.out.println("A");
    }
}

class B12 extends A12 {

    B12() {
        System.out.println("B");
    }
}

public class TestConstrutor {
    public static void main(String[] args) {
        new B12();
    }
}