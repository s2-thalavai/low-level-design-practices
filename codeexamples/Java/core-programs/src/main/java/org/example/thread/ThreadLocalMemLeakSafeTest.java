package org.example.thread;
/*

        | Concept                | Behavior                   |
        | ---------------------- | -------------------------- |
        | ThreadLocal            | Per-thread storage         |
        | New thread             | Gets `null`                |
        | InheritableThreadLocal | Copies parent value        |
        | ThreadPool             | ⚠️ Dangerous (reuse issue) |
        | remove()               | Prevent memory leaks       |


 */
public class ThreadLocalMemLeakSafeTest {

    static ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {

        Thread t = new Thread(() -> {
            try {
                local.set(10);
                System.out.println("Value: " + local.get());
            } finally {
                local.remove(); // VERY IMPORTANT
            }
        });

        t.start();
        t.join();
    }
}