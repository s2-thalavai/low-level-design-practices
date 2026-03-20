package org.example.thread;

import java.util.concurrent.*;

public class ProducerConsumerBQ {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer
        new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    queue.put(value); // waits if full
                    System.out.println("Produced: " + value++);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Consumer
        new Thread(() -> {
            try {
                while (true) {
                    int value = queue.take(); // waits if empty
                    System.out.println("Consumed: " + value);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}