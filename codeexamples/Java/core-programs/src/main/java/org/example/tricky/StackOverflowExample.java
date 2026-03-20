package org.example.tricky;

public class StackOverflowExample {

    static void recurse() {

        recurse();   // infinite recursion
    }

    public static void main(String[] args) {

        recurse();
    }
}