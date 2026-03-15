package org.example.core;

abstract class SampleAbstractClass {

    String name;

    private static String companyName = "Marlabs LLC";

    private static final String PI = "3.14";

    static {
        System.out.println("Static Block");
    }

    {
        System.out.println("Instance Block");
    }

    SampleAbstractClass() {
        System.out.println("SampleAbstractClass Constructor");
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        SampleAbstractClass.companyName = companyName;
    }

    private void testPrivateMethod() {
        System.out.println("testPrivateMethod");
    }

    protected void testProtectedMethod() {
        System.out.println("testProtectedMethod");
    }

    public void testPublicMethod() {
        System.out.println("testPublicMethod");
    }

    void testDefaultMethod() {

    }
}

class ChildClass extends SampleAbstractClass {

}

public class AbstractClassDemo {

    public static void main(String[] args) {

        System.out.println(SampleAbstractClass.getCompanyName());

        // System.out.println(SampleAbstractClass.testProtectedMethod());
        //  System.out.println(SampleAbstractClass.testPublicMethod());
        // SampleAbstractClass sac = new SampleAbstractClass();

        ChildClass obj = new ChildClass();

        obj.testProtectedMethod();
        obj.testPublicMethod();

    }
}
