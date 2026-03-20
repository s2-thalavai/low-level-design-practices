package org.example.oop;
/*

    Static → compile-time
    Instance → runtime

    Upcasting Puzzle
    A obj = new B();
    Allowed because B is-a A.

    Downcasting Puzzle
    A obj = new B();
    B b = (B) obj;
    Works because object is actually B.

    Invalid Downcast
    A obj = new A();
    B b = (B) obj;
    Result
    ClassCastException



 */
class Asi {
    static void show() { System.out.println("A"); }
    void print() { System.out.println("A print"); }
}

class Bsi extends Asi {
    static void show() { System.out.println("B"); }
    void print() { System.out.println("B print"); }
}


public class StaticAndInstanceDemo {

    public static void main(String[] args){

        Asi obj = new Bsi();

        obj.show();
        obj.print();
    }
}
