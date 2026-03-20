package org.example.jvm;
/*


 */
class Test456 {}

public class StrongRefExample {

    public static void main(String[] args) {

        Test456 t = new Test456(); // strong reference

        System.gc(); // not force GC

        System.out.println(t); // object still alive

        // NOT eligible for GC
        // As long as a strong reference exists, object will NOT be collected.
    }
}