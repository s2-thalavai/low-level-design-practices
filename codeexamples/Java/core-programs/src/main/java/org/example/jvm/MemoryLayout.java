package org.example.jvm;

/*

    Stack (main thread stack frame)

    Stack
    -------------------
        a = 10
        s → reference
        t → reference

        a → primitive stored directly in stack

        s → reference stored in stack

        t → reference stored in stack

    Heap
    -------------------
    Test object

    The object created using:

        new Test()

    is stored in the heap.

    String Pool (inside Heap)

    String Pool
    -------------------
    "Java"

    Important point:

    String literals are stored in the String Pool.

    The String Pool is a special area inside the heap.

    Final Visualization

                   JVM MEMORY

    Stack
    --------------------------------
    main()
       a = 10
       s ───────────────┐
       t ────────────┐  │
                      │  │
    Heap              │  │
    --------------------------------
    Test object <─────┘  │
                         │
    String Pool          │
    --------------------------------
    "Java" <─────────────┘

    Key Rules

    | Variable Type             | Stored In                 |
    | ------------------------- | ------------------------- |
    | Primitive local variables | Stack                     |
    | Object references         | Stack                     |
    | Objects                   | Heap                      |
    | String literals           | String Pool (inside heap) |

 */

public class MemoryLayout {

    public static void main(String[] args) {

        int a = 10;

        String s = "Java";

        Test t = new Test();

        // Heap object → eligible
        // Pool object → NOT eligible
        String s1 = new String("Java");
        s1 = null;

        // "Java" still remains in pool
        //Strings are immutable.
        String s11 = "Java";
        s11 = "Python";



    }
}
