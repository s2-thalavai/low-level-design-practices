package org.example.jvm;
/*

            Object collected immediately if no strong references exist.

        Weak references are used in:

            caches

            maps

            memory-sensitive structures

        Example:

            WeakHashMap


            Does it force GC?
            No
            It only suggests GC to JVM.
            JVM may ignore it.

 */
import java.lang.ref.WeakReference;

class Test78 {}

public class WeakRefExample {

    public static void main(String[] args) {

        WeakReference<Test78> ref =
                new WeakReference<>(new Test78());

        System.gc(); // not force GC

        System.out.println(ref.get()); // likely null

       // Collected as soon as GC runs
       // Use Case
       // WeakHashMap, canonical mappings


    }
}