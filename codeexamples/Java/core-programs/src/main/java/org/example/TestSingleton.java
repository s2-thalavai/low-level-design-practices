package org.example;

class Singleton {

    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {

        if(instance == null) {

            synchronized(Singleton.class) {

                if(instance == null) {
                    instance = new Singleton();
                }

            }
        }

        return instance;
    }
}

public class TestSingleton {

    public static void main(String[] args) {

        Runnable task = () -> {
            Singleton s = Singleton.getInstance();
            System.out.println(s.hashCode());
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
    }
}