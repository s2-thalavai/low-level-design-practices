package org.example.thread;

public class ThreadLifecycleDemo {

    public static void main(String[] args) throws Exception {

        Object lock = new Object();

        Thread t = new Thread(() -> {
            try {
                synchronized (lock) {
                    System.out.println("Thread acquired lock");

                    // TIMED_WAITING
                    Thread.sleep(1000);

                    // WAITING
                    lock.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.getState()); // NEW

        t.start();
        System.out.println(t.getState()); // RUNNABLE

        Thread.sleep(100); // let thread start

        System.out.println(t.getState()); // TIMED_WAITING / WAITING

        synchronized (lock) {
            lock.notify();
        }

        t.join();

        System.out.println(t.getState()); // TERMINATED
    }
}