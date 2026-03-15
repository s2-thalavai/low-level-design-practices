package org.example.core;

interface Payment {
    void pay(double amount);
    void refundV1(double amount);

    // Backward compatibility
    default void refund(double amount) {
        System.out.println("Default refund logic");
    }

    static void validate(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}

interface Auditable {

    void audit(String transactionId, String from);
    void auditV1(String transactionId, String from, String to);
}

abstract class BankPayment implements Payment, Auditable {

    String bankName;

    BankPayment(String bankName) {
        this.bankName = bankName;
    }

    public void validate(String from) {
        System.out.println("Validating " + from + " bank details...");
    }

    @Override
    public void audit(String transactionId, String from) {
        System.out.println("Auditing " + from + " transaction: " + transactionId);
    }

    @Override
    public void auditV1(String transactionId, String from, String to) {
        System.out.println("Auditing " + from + " transaction: " + transactionId + " To: " + to);
    }
}

class CreditCardPayment extends BankPayment {

    CreditCardPayment(String bankName) {
        super(bankName);
    }

    @Override
    public void pay(double amount) {
        String from = this.getClass().getSimpleName();
        validate(from);
        System.out.println("Paid from " + from + " : " + amount);
        audit("TXN123", from);
    }

    @Override
    public void refundV1(double amount) {
        System.out.println("Money Refunded from CreditCardPayment : " + amount);
    }
}

class DebitCardPayment extends BankPayment {

    DebitCardPayment(String bankName) {
        super(bankName);
    }

    @Override
    public void pay(double amount) {
        String from = this.getClass().getSimpleName();
        validate(from);
        System.out.println("Paid from " + from + " : " + amount);
        audit("TXN456", from);
    }

    @Override
    public void refundV1(double amount) {
        System.out.println("Refund from DebitCardPayment : " + amount);
    }
}

class UPIPayment extends BankPayment {

    UPIPayment(String bankName) {
        super(bankName);
    }

    @Override
    public void pay(double amount) {
        String from = this.getClass().getSimpleName();
        validate(from);
        System.out.println("Paid from " + from + " : " + amount);
        audit("TXN789", from);
    }

    @Override
    public void refundV1(double amount) {
        System.out.println("Refund from UPIPayment : " + amount);
    }
}

public class InterfaceWithAbstractClass {

    public static void main(String[] args) {

        Payment payment = new CreditCardPayment("HDFC");
        payment.pay(50000);

        Payment payment1 = new DebitCardPayment("SBI");
        Payment payment2 = new UPIPayment("ICICI");

        payment.refundV1(1000);

        payment1.pay(500);
        payment2.pay(1500);
    }
}