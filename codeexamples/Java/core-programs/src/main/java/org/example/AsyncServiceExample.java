package org.example;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AsyncServiceExample {

    public static void main(String[] args) {

        List<Integer> list = List.of(1,2,3,4,5,6,7,8);

        ExecutorService executor = Executors.newFixedThreadPool(20);

        List<CompletableFuture<String>> futures =
                list.stream()
                        .map(id -> CompletableFuture.supplyAsync(
                                () -> callRemoteService(id),
                                executor
                        ))
                        .collect(Collectors.toList());

        List<String> results =
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());

        executor.shutdown();

        System.out.println(results);
    }

    static String callRemoteService(Integer id) {
        try {
            Thread.sleep(1000); // simulate remote API call
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Response-" + id;
    }
}