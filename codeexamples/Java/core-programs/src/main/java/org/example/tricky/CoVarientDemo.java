package org.example.tricky;

/*
    Parent return type → Object
    Child return type  → String

    Since:

        String IS-A Object

        this is allowed.

        This is covariant return type.

*/

class ParentA {

    Object getValue() {
        return "Parent";
    }
}

class ChildA extends ParentA {

    @Override
    String getValue() {  // Covariant return type
        return "Child";
    }
}

public class CoVarientDemo {

    public static void main (String[] args) {

        ParentA p = new ChildA();
        System.out.println(p.getValue());
    }
}
