package org.example.oop;

class AS {

    AS(int x) { }
}

class BS extends AS {

    // BS() { } compile Time Error

    BS(int x) {
        super(x);
    }


}

public class SuperConstructorDemo {

    public static void main(String[] args)  {

        new BS(10);
    }
}