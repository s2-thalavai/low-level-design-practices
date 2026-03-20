package org.example.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class ProducerConsumerSemaphore {

    private final Semaphore empty = new Semaphore(5);
    private final Semaphore full = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    private final Queue<Integer> queue = new LinkedList<>();

    public void produce(int value) throws InterruptedException {
        empty.acquire();
        mutex.acquire();

        queue.add(value);
        System.out.println("Produced: " + value);

        mutex.release();
        full.release();
    }

    public int consume() throws InterruptedException {
        full.acquire();
        mutex.acquire();

        int value = queue.poll();
        System.out.println("Consumed: " + value);

        mutex.release();
        empty.release();

        return value;
    }
}