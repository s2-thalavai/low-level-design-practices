package org.example.jvm;
/*

        Only when memory pressure occurs.

        Used for:

        memory-sensitive caches

        Use Case
        Caches (image cache, data cache)

 */
import java.lang.ref.SoftReference;

class Test67 {}

public class SoftRefExample {
    public static void main(String[] args) {

        SoftReference<Test67> ref =
                new SoftReference<>(new Test67());

        System.gc(); // not force GC

        System.out.println(ref.get()); // may or may not be null
        // Collected only when memory is low
    }
}