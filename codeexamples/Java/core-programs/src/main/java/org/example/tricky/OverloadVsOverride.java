package org.example.tricky;

// Method chosen at compile time based on reference type.

/*

1️⃣ Method Overloading → resolved at compile time
2️⃣ Method Overriding → resolved at runtime

 */
class AA {

    void print(int a) {
        System.out.println("A");
    }

    void print1(int a) {
        System.out.println("print 1 from AA Class");
    }
}

class BB extends AA {

    void print(long a) {
        System.out.println("B");
    }

    void print1(int a) {
        System.out.println("print 1 from BB Class");
    }
}

public class OverloadVsOverride {

     public static void main (String[] args) {
         System.out.println("Method chosen at compile time based on reference type.");
         AA obj = new BB();
         obj.print(10);   // A. A method signature int  B method signature Long. Overloading

         System.out.println("Method chosen at compile time based on reference type.");
         BB obj1 = new BB();
         obj1.print(10);  // A. A method signature int  B method signature Long. Overloading


         System.out.println("Method chosen at compile time based on reference type.");
         AA obj2 = new BB();
         obj.print1(10);   // print 1 from BB Class

         System.out.println("Method chosen at compile time based on reference type.");
         BB obj3 = new BB();
         obj1.print1(10);  // print 1 from BB Class
     }
 }
