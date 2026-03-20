package org.example.jvm;
/*

        Used for post-GC cleanup tracking

        get() always returns null

        Object already finalized when enqueued

        Used for:

        post-mortem cleanup
        memory management
        off-heap resources

        Object already phantom reachable.

 */
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

class Test89 {}

public class PhantomRefExample {
    public static void main(String[] args) {

        ReferenceQueue<Test89> queue = new ReferenceQueue<>();

        Test89 t = new Test89();

        PhantomReference<Test89> ref =
                new PhantomReference<>(t, queue);

        t = null;

        System.gc(); // // not force GC

        System.out.println(ref.get()); // always null
        System.out.println(queue.poll()); // reference appears here
    }
}