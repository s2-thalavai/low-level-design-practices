package org.example;

import java.util.*;

public class TopKNumbersPriority {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10, 50, 20, 40, 30, 60);

        int k = 3;

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : list) {

            minHeap.add(num);

            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        System.out.println(minHeap);
    }
}