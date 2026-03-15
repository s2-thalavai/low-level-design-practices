package org.example.core;

interface SampleInterface {

    String name = "";

//    private static String companyName = "Marlabs LLC";
//    private static final String PI = "3.14";

//    static {
//        System.out.println("Static Block");
//    }
//
//    {
//        System.out.println("Instance Block");
//    }
//
//    SampleInterface() {
//        System.out.println("SampleInterface Constructor");
//    }

//    public static String getCompanyName() {
//        return companyName;
//    }
//
//    public static void setCompanyName(String companyName) {
//        SampleAbstractClass.companyName = companyName;
//    }

    private void testPrivateMethod() {
        System.out.println("testPrivateMethod");
    }

//    protected void testProtectedMethod() {
//        System.out.println("testProtectedMethod");
//    }

//    public void testPublicMethod() {
//        System.out.println("testPublicMethod");
//    }

    default void testPublicMethod() {
        System.out.println("testPublicMethod");
    }

    static void testDefaultMethod() {
        System.out.println("static method");
    }
}


public class SampleInterfaceDemo {

    public static void main(String[] args) {

        System.out.println("SampleInterfaceDemo");

        //  System.out.println(SampleAbstractClass.testProtectedMethod());
        //  System.out.println(SampleAbstractClass.testPublicMethod());
        //  SampleAbstractClass sac = new SampleAbstractClass();

    }
}