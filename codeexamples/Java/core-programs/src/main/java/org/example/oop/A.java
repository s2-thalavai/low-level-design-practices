package org.example.oop;

interface Aid {
    default void print() {}
}

interface Bid {
    default void print() {}
}

class InterfaceMultipleDefaultTest implements Aid,Bid {

    @Override
    public void print() {
        Aid.super.print();
        Bid.super.print();
    }
}