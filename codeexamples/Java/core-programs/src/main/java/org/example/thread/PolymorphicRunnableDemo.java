package org.example.thread;

public class PolymorphicRunnableDemo {

    public static void main(String[] args) {

        // 1. Interface reference pointing to Thread object
        Runnable r = new Thread();

        System.out.println("Calling run() using Runnable reference:");
        r.run(); // Just normal method call (NO new thread)

        // 2. Proper way to start a thread
        Thread t1 = new Thread(() -> {
            System.out.println("Running inside new thread: " + Thread.currentThread().getName());
        });

        t1.start(); // Creates new thread

        // 3. Using Runnable implementation (Best Practice)
        Runnable task = new MyTask();    // Separation of task and thread

        Thread t2 = new Thread(task);
        t2.start();

        // 4. Downcasting example (Safe)
        Runnable r2 = new Thread(() -> {
            System.out.println("Thread via casting: " + Thread.currentThread().getName());
        });

        Thread t3 = (Thread) r2; // Safe (actual object is Thread)
        t3.start();

        // 5. Downcasting example (Unsafe)
        Runnable r3 = new MyTask();

        try {
            Thread t4 = (Thread) r3; // Runtime error
            t4.start();
        } catch (ClassCastException e) {
            System.out.println(" Cannot cast MyTask to Thread: " + e);
        }
    }
}

// Custom Runnable implementation
class MyTask implements Runnable {

    @Override
    public void run() {

        System.out.println("Running MyTask in thread: " + Thread.currentThread().getName());
    }
}