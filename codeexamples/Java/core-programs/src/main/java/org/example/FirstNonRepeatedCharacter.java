package org.example;

import java.util.*;

public class FirstNonRepeatedCharacter {

    public static void main(String[] args) {

        String input = "swiss";

        Map<Character, Integer> map = new LinkedHashMap<>();

        // Count frequency
        for (char c : input.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // Find first non-repeated character
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("First non-repeated character: " + entry.getKey());
                return;
            }
        }

        System.out.println("No non-repeated character found");
    }
}