package org.example.tricky;

/*
        Why volatile is needed

        Without volatile, the worker thread might cache running = true and loop forever.

        volatile guarantees visibility across threads.
 */
public class volatileTest {

   //  static boolean running = true;

    static volatile boolean running = true;

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            while (running) {
                // busy wait
            }
            System.out.println("Thread stopped");
        });

        t.start();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        running = false;
        System.out.println("Main thread changed running to false");
    }
}