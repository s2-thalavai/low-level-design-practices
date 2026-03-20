package org.example.thread;
/*

        Possible Outputs

        AB
        BA

        Thread scheduling unpredictable.

        AAABBB
        BBBAAA
        ABABAB
        AABBAB

        Many combinations possible.

 */
public class ThreadSchedulingORder {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> System.out.print("A"));

        t.start();

        System.out.print("B"); // BA / AB Thread scheduling unpredictable.

        System.out.println();

        new Thread(() -> {
            for(int i=0;i<3;i++)
                System.out.print("A");
        }).start();

        new Thread(() -> {
            for(int i=0;i<3;i++)
                System.out.print("B");
        }).start();


        Thread t1 = new Thread(() -> System.out.print("A"));

        t1.start();
        t1.join(); // join() ensures thread finishes first.

        System.out.print("B");

    }
}