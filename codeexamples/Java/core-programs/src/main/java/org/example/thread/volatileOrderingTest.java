package org.example.thread;
/*

        volatile guarantees:

        ✔ 1. Visibility

        Changes are immediately visible to other threads

        ✔ 2. Ordering (VERY IMPORTANT)

        Prevents reordering before/after volatile write
 */

public class volatileOrderingTest {

    static int x = 0;
    static volatile boolean flag = false;

    public static void main(String[] args) {

        new Thread(() -> {
            x = 10;
            flag = true;
        }).start();

        new Thread(() -> {
            if (flag) {
                System.out.println(x); // always 10
            }
        }).start();
    }
}