package org.example;

enum SingletonEnum {

    INSTANCE;

    private int counter = 0;

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}

public class TestEnumSingleton {

    public static void main(String[] args) {

        SingletonEnum.INSTANCE.increment();
        SingletonEnum.INSTANCE.increment();

        System.out.println(SingletonEnum.INSTANCE.getCounter());

    }
}

