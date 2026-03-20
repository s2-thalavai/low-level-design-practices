package org.example.tricky;

/*
$ java StaticWithoutMainTest.java
Hello

 */
public class StaticWithoutMainTest {

    static {
        System.out.println("Hello");
        System.exit(0);
    }
}