package org.example.oop;

interface AIs {
    void print();
}
interface BIs {
    void print();
}

public class InterfaceMultipleAbstractTest implements AIs, BIs {

    public void print() {

        System.out.println("Hello");
    }
}