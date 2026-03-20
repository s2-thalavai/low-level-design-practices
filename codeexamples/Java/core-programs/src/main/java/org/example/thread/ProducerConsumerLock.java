package org.example.thread;

import java.util.*;
import java.util.concurrent.locks.*;

public class ProducerConsumerLock {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 5;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }

            queue.add(value);
            System.out.println("Produced: " + value);

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int consume() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }

            int value = queue.poll();
            System.out.println("Consumed: " + value);

            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }
}