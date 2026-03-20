package org.example.jvm;

/*

// This prints GC stats every second.
// Run JVM with smaller heap:

    java -Xss900m MemoryLeakExample

 */
public class StackMonitor {

    static void recurse(int depth) {

        int stackDepth = Thread.currentThread().getStackTrace().length;

        System.out.println("Recursion depth = " + depth +
                " | Stack frames = " + stackDepth);

        recurse(depth + 1);
    }

    public String test() {
        String s = new String("Java");
        return s;
    }

    public static void main(String[] args) {

        recurse(1);
    }
}