package org.example.jvm;

// This causes StackOverflowError because base condition is missing.
public class Factorial {

    static int fact(int n) {

         // if (n == 1)
         //   return 1; // base condition

        return n * fact(n - 1);
    }

    public static void main(String[] args) {

        System.out.println(fact(5));
    }
}