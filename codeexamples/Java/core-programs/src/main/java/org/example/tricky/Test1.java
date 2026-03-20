package org.example.tricky;
/*
    Class initialization happens top → down in inheritance hierarchy.

    During class loading:

        1️⃣ Parent class static variables
        2️⃣ Parent class static blocks
        3️⃣ Child class static variables
        4️⃣ Child class static blocks

        So the order is:

        Parent → Child

 */
class Parent1 {

    static int x = 10;
    static int y = 20;

    static {
        System.out.println("Parent static block x = " + x + " y : " + y);
        x = 100;
        y = 200;
        System.out.println("Parent static x = "+ x + " y : " + y);

    }


}

class Child1 extends Parent1 {

    static int x = 30;
    static {
        System.out.println("Child staticx = " + x );
        x = 300;
        System.out.println("Child static x = "+ x);
        y = 400;

    }
    static int y = 40;
}

public class Test1 {
    public static void main(String[] args) {

        Parent1 p = new Child1();
        System.out.println(" parent static y = "+ p.y); // 200 top-down order

        Child1 c1 = new Child1();
        System.out.println(" Child1 static y = "+ c1.y); // 40 top-down order

    }
}