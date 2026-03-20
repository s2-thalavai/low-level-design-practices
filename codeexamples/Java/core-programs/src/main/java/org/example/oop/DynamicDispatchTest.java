package org.example.oop;
/*

        | Concept               | Meaning                                                       |
        | --------------------- | ------------------------------------------------------------- |
        | **Method Overriding** | Writing a method in child class with same signature as parent |
        | **Dynamic Dispatch**  | JVM deciding **at runtime** which overridden method to call   |


    Step-by-step:

        Compile time

            Compiler checks method in type A

            OK → method exists

        Runtime

            Actual object = B

            JVM calls B.show()

        This runtime decision = Dynamic Method Dispatch

        Overriding enables dynamic dispatch


 */
class Adis {
    void show() {

        System.out.println("A");
    }
}

class Bdis extends Adis {

    void show() {

        System.out.println("B");
    }
}

public class DynamicDispatchTest {

    public static void main(String[] args) {

        Adis obj = new Bdis();  // parent reference, child object, Runtime poly
        obj.show();       // dynamic dispatch
    }
}