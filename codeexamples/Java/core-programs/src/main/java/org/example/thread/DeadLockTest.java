package org.example.thread;

public class DeadLockTest {

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {

        new Thread(() -> {

            synchronized(lock1) {

                synchronized(lock2) {
                    System.out.println("Thread1");
                }

            }

        }).start();

        new Thread(() -> {

            synchronized(lock2) {

                synchronized(lock1) {
                    System.out.println("Thread2");
                }

            }

        }).start();

    }
}