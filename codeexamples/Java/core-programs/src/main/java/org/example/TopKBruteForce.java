package org.example;

import java.util.*;

public class TopKBruteForce {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(Arrays.asList(10, 50, 20, 40, 30, 60));

        int k = 3;

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {

            int max = Integer.MIN_VALUE;
            int index = -1;

            for (int j = 0; j < list.size(); j++) {

                if (list.get(j) > max) {
                    max = list.get(j);
                    index = j;
                }
            }

            result.add(max);
            list.remove(index);
        }

        System.out.println(result);
    }
}