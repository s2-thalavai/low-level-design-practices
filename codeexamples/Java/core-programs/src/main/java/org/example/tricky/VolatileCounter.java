package org.example.tricky;

import java.util.concurrent.atomic.AtomicInteger;

/*

   Sometimes

        > Task :org.example.tricky.VolatileCounter.main()
        > Task :org.example.tricky.VolatileCounter.main()

        Count before start SCount = 0
        Count before start SVCount = 0
        Count before start CASCount = 0

        Count before Join SCount = 0
        Count before Join SVCount = 5
        Count before Join CASCount = 10

        Count After T1 Join SCount == 10
        Count After T1 Join SVCount == 10
        Count After T1 Join CASCount == 10

        Count After T2 Join = 10
        Count After T2 Join SVCount = 10
        Count After T2 Join CASCount = 10

        BUILD SUCCESSFUL in 331ms


 */
public class VolatileCounter {

   static volatile int SVCount = 0;  // not guarantees the atomicity

   static int SCount = 0;

    static AtomicInteger CASCount = new AtomicInteger(0);


    public static void main(String[] args) throws Exception {

        Runnable task = () -> {
            for(int i=0;i<5;i++) {
                SVCount++;
                SCount++;
                CASCount.incrementAndGet();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        System.out.println("Count before start SCount = " + SCount);
        System.out.println("Count before start SVCount = " + SVCount);
        System.out.println("Count before start CASCount = " + CASCount);

        t1.start();
        t2.start();

        System.out.println("Count before Join SCount = " + SCount);
        System.out.println("Count before Join SVCount = " + SVCount);
        System.out.println("Count before Join CASCount = " + CASCount);

        t1.join();

        System.out.println("Count After T1 Join SCount == " + SCount);
        System.out.println("Count After T1 Join SVCount == " + SVCount);
        System.out.println("Count After T1 Join CASCount == " + CASCount);

        t2.join();

        System.out.println("Count After T2 Join SCount = " + SCount);
        System.out.println("Count After T2 Join SVCount = " + SVCount);
        System.out.println("Count After T2 Join CASCount = " + CASCount);

    }
}