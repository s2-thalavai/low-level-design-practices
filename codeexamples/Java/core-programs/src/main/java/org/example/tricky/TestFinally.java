package org.example.tricky;

public class TestFinally {

    static int test() {

        int x = 1;

        try {
            return x; // Return value stored before finally executes.
        } finally {
            x = 2;
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}