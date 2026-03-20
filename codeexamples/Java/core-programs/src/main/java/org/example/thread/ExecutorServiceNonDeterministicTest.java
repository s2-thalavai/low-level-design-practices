package org.example.thread;
/*

        Possible Outputs

            AB
            BA

        newFixedThreadPool(2) creates:

            2 worker threads + 1 shared queue

        Tasks are executed by different threads concurrently

            Pool size = 2

        Both tasks can run in parallel

        --------

        A queue holds submitted tasks

        Two worker threads pick tasks

        Scenario 1

            Thread-1 → prints A
            Thread-2 → prints B
            Output → AB

        Scenario 2

            Thread-2 → prints B
            Thread-1 → prints A
            Output → BA

         -----

         Submission order ≠ Execution order

        Even though:

            submit(A)
            submit(B)

        Execution order is not guaranteed

        -----------

        Executors.newFixedThreadPool(1);

        Output becomes always:

            AB

        Because:

            Single worker thread

            Tasks executed sequentially

 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceNonDeterministicTest {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        executor.submit(() -> System.out.print("A"));
        executor.submit(() -> System.out.print("B"));

        executor.shutdown();
    }
}