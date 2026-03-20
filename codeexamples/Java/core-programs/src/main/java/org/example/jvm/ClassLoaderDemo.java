package org.example.jvm;

public class ClassLoaderDemo {

    public static void main(String[] args) throws Exception {

        String path = "out/production/classes/org/example/classloader";

        MyClassLoader loader1 = new MyClassLoader(path);
        MyClassLoader loader2 = new MyClassLoader(path);

        Class<?> c1 = loader1.loadClass("org.example.classloader.Test");
        Class<?> c2 = loader2.loadClass("org.example.classloader.Test");

        System.out.println("c1 == c2 ? " + (c1 == c2));

        Object obj = c1.getDeclaredConstructor().newInstance();

        // This will throw ClassCastException
        Test123 t = (Test123) obj;

        t.hello();
    }
}