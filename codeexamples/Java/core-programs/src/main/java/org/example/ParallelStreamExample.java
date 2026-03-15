package org.example;

import java.util.List;

public class ParallelStreamExample {

    public static void main(String[] args) {

        List<Integer> list = List.of(1,2,3,4,5,6,7,8);

        List<String> result =
                list.parallelStream()
                        .map(ParallelStreamExample::callRemoteService)
                        .toList();

        System.out.println(result);
    }

    static String callRemoteService(Integer id) {
        try {
            Thread.sleep(1000); // simulate network call
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Response-" + id;
    }
}