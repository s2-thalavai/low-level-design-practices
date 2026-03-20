package org.example.thread;

import java.util.*;

public class ProducerConsumerSync {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 5;

    public synchronized void produce(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // wait if full
        }

        queue.add(value);
        System.out.println("Produced: " + value);

        notifyAll(); // notify consumers
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait if empty
        }

        int value = queue.poll();
        System.out.println("Consumed: " + value);

        notifyAll(); // notify producers
        return value;
    }
}