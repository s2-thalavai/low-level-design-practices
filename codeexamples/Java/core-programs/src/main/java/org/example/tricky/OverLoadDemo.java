package org.example.tricky;

/*

    Java determines method overloading using only:

        method name + parameter list

    In this example both methods are seen as:

        print()

    Return type is ignored during overload resolution.

    So the compiler thinks you're defining the same method twice.

 */
class TestQ {

    int print() {
        return 1;
    }

//    double print() {   // compile error. method print() is already defined in class Test
//        return 2.5;
//    }

    int print(int a) {
        return a;
    }

    double print(double a) {
        return a;
    }

    public static void main(String[] args) {

        TestQ t = new TestQ();
        System.out.println(t.print());

        System.out.println(t.print(10));
        System.out.println(t.print(5.5));
    }
}
public class OverLoadDemo {
}
