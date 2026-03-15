package org.example;

import java.lang.reflect.Constructor;

public class ReflectionBreak {

    public static void main(String[] args) throws Exception {

        Singleton s1 = Singleton.getInstance();

        Constructor<Singleton> constructor =
                Singleton.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        Singleton s2 = constructor.newInstance();

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }
}