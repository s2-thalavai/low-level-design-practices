package org.example.tricky;
/*

    JVM Memory Layout

    When this program runs, the JVM uses three main memory areas.

    1. Method Area (Metaspace)
    2. Stack
    3. Heap


    Method Area
    -------------------
    Class: Test
    static int x = 10
    main() method info
    bytecode

    Ex: static x

    Stack
    -------------------
    Each thread has its own stack.
    When main() starts, a stack frame is created.

    Stack (Main Thread)
    -------------------
    main()
       y = 20

    Local variables like y exist only inside the stack frame.

    When main() finishes, this frame disappears.


    Heap
       Objects

                      JVM MEMORY

   ┌──────────────────────────┐
   │       Method Area        │
   │--------------------------│
   │ Class: Test              │
   │ static int x = 10        │
   │ main() bytecode          │
   └──────────────────────────┘


   ┌──────────────────────────┐
   │          Stack           │
   │--------------------------│
   │ main() frame             │
   │ int y = 20               │
   └──────────────────────────┘


   ┌──────────────────────────┐
   │           Heap           │
   │--------------------------│
   │ (no objects created)     │
        t
   └──────────────────────────┘
 */

public class TestJVMMemoryLayout {

    static int x = 10; // method area

    public static void main(String[] args) {

        int y = 20; // local variable

        Test t = new Test(); // heap

        Test t1 = new Test();
        Test t2 = new Test();

        t1.x = 20;

        // Test.x = 20; correct way
        System.out.println(t2.x); // 20 means only one copy of x exists per class, not per object.

        Test t11 = null;
        System.out.println(t11.x);


    }
}