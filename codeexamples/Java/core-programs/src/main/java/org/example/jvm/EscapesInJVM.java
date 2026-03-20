package org.example.jvm;

/*

1. No Escape (Best Case)

    The object:

    StringBuilder

    does not escape the method.

        JVM may optimize by:

        stack allocation
        or
        scalar replacement

    sb is used only inside test()

    ----------

    2.  Method Escape

    The object leaves the method (returned or passed outside).

    Explanation

        sb is returned

        So the object escapes the method.

        Therefore:

        Must be allocated on heap

    ----------

    3. Thread Escape

        The object is shared across threads.

        Example:

        sb is static, so Other threads can access it.

        So the object:

            escapes the thread

        Therefore JVM must allocate on heap.

        -----------

        No Escape
        ---------
        method()
           object
           ↓
        used locally only
        → stack allocation possible


        Method Escape
        -------------
        method()
           object
           ↓
        returned to caller
        → heap allocation


        Thread Escape
        -------------
        object stored in shared field
        → heap allocation

        -----------

 */

class Test {

    static StringBuilder sb;

    public void test() {
        sb = new StringBuilder();
        sb.append("Java");
    }
}

public class EscapesInJVM {

    public void testNoEscape() {
        StringBuilder sb = new StringBuilder(); // sb does not escape the method.
        sb.append("Java");
        System.out.println(sb.toString());
    }

    public StringBuilder testEscapeMethod() {
        StringBuilder sb = new StringBuilder();
        sb.append("Java");
        return sb;
    }


    public static void main(String[] args) {


    }
}
