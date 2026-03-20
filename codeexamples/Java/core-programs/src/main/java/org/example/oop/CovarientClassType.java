package org.example.oop;
/*

        In method overriding, Java allows:

        Return type in child class can be a subtype of parent’s return type

        What is happening

            Parent method returns: A

            Child method returns: B (which is-a A)

        ✔ So it's type-safe

            class B

        Even though reference is A, actual object is B

 */
class Az {

    Az get() {
        return this;
    }
}

class Bz extends Az {

    @Override
    Bz get() {
        return this;
    }
}

public class CovarientClassType {

    public static void main(String[] args) {

        Az obj = new Bz();
        Az result = obj.get();

        System.out.println(result.getClass()); // Bz

        Az obj1 = new Bz();
       //  B b = obj1.get(); // Compilation error

        // Why?
        // Compile-time type = A
        //Method signature seen = A get()
        // Fix:

        Bz b = (Bz) obj.get();
    }
}
