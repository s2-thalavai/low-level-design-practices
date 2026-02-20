// 1️⃣ INTERFACE SEGREGATION + ABSTRACTION
interface PaymentMethod {
  pay(amount: number): void;
}

interface Refundable {
  refund(amount: number): void;
}

// 2️⃣ DEPENDENCY INVERSION (high-level depends on abstraction)
interface Logger {
  log(message: string): void;
}

// Concrete Logger
class ConsoleLogger implements Logger {
  log(message: string): void {
    console.log(`[LOG]: ${message}`);
  }
}

// 3️⃣ ABSTRACT CLASS (Abstraction)
abstract class BasePayment implements PaymentMethod {
  constructor(protected logger: Logger) {}

  // Encapsulation (protected state)
  protected validate(amount: number): void {
    if (amount <= 0) {
      throw new Error("Invalid payment amount");
    }
  }

  abstract pay(amount: number): void;
}

// 4️⃣ INHERITANCE
class CreditCardPayment extends BasePayment implements Refundable {
  constructor(logger: Logger, private cardNumber: string) {
    super(logger);
  }

  // 5️⃣ POLYMORPHISM (method overriding)
  override pay(amount: number): void {
    this.validate(amount);
    this.logger.log(`Paid ₹${amount} using Credit Card`);
  }

  refund(amount: number): void {
    this.logger.log(`Refunded ₹${amount} to Credit Card`);
  }
}

class UpiPayment extends BasePayment {
  constructor(logger: Logger, private upiId: string) {
    super(logger);
  }

  override pay(amount: number): void {
    this.validate(amount);
    this.logger.log(`Paid ₹${amount} using UPI`);
  }
}

// 6️⃣ SINGLE RESPONSIBILITY
// This class only processes payments, nothing else.
class PaymentProcessor {
  constructor(private paymentMethod: PaymentMethod) {}

  process(amount: number): void {
    this.paymentMethod.pay(amount);
  }
}

// 7️⃣ OPEN/CLOSED PRINCIPLE
// We can add new payment types WITHOUT modifying PaymentProcessor.

// 8️⃣ LISKOV SUBSTITUTION
// Any subclass of BasePayment works in place of PaymentMethod.

// 9️⃣ ENCAPSULATION
class BankAccount {
  private balance: number = 0;

  deposit(amount: number): void {
    if (amount <= 0) throw new Error("Invalid deposit");
    this.balance += amount;
  }

  getBalance(): number {
    return this.balance;
  }
}

// 🔟 COMPOSITION OVER INHERITANCE
// Instead of extending logger, we inject it (composition).

// ------------------ USAGE ------------------

const logger = new ConsoleLogger();

const creditCard = new CreditCardPayment(logger, "1234-5678");
const upi = new UpiPayment(logger, "siva@upi");

const processor1 = new PaymentProcessor(creditCard);
processor1.process(5000);

const processor2 = new PaymentProcessor(upi);
processor2.process(2500);
