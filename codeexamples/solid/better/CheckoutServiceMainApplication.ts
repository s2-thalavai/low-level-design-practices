/**
 * ===============================
 * 1. Abstraction (DIP)
 * ===============================
 */
interface PaymentProcessor {
  pay(amount: number): void;
}

/**
 * ===============================
 * 2. Concrete Implementations (SRP)
 * ===============================
 */
class StripePaymentProcessor implements PaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using Stripe";

  pay(amount: number): void {
    console.info(
      StripePaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

class RazorpayPaymentProcessor implements PaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using Razorpay";

  pay(amount: number): void {
    console.info(
      RazorpayPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

class PayPalPaymentProcessor implements PaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using PayPal";

  pay(amount: number): void {
    console.info(
      PayPalPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

/**
 * ===============================
 * 3. Payment Provider Enum
 * ===============================
 */
enum PaymentProvider {
  STRIPE = "STRIPE",
  RAZORPAY = "RAZORPAY",
  PAYPAL = "PAYPAL",
}

/**
 * ===============================
 * 4. Factory (OCP)
 * ===============================
 */
class PaymentProcessorFactory {
  constructor(
    private readonly processors: Record<PaymentProvider, PaymentProcessor>
  ) {}

  get(provider: PaymentProvider): PaymentProcessor {
    const processor = this.processors[provider];

    if (!processor) {
      throw new Error(`Unsupported payment provider: ${provider}`);
    }

    return processor;
  }
}

/**
 * ===============================
 * 5. Business Logic (SRP + DIP)
 * ===============================
 */
class CheckoutService {
  constructor(
    private readonly factory: PaymentProcessorFactory
  ) {}

  checkout(amount: number, provider: PaymentProvider): void {
    if (amount <= 0) {
      throw new Error("Amount must be greater than zero");
    }

    console.info(`Starting checkout with provider: ${provider}`);

    const processor = this.factory.get(provider);
    processor.pay(amount);
  }
}

/**
 * ===============================
 * 6. Main Entry Point
 * ===============================
 */
class CheckoutServiceMainApplication {
  static main(): void {
    const factory = new PaymentProcessorFactory({
      [PaymentProvider.STRIPE]: new StripePaymentProcessor(),
      [PaymentProvider.RAZORPAY]: new RazorpayPaymentProcessor(),
      [PaymentProvider.PAYPAL]: new PayPalPaymentProcessor(),
    });

    const checkoutService = new CheckoutService(factory);

    checkoutService.checkout(1500, PaymentProvider.STRIPE);
  }
}

// Run
CheckoutServiceMainApplication.main();

/*
 *
 * ===============================
 * Output    
 * ===============================
 * 
s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ tsc CheckoutServiceMainApplication.ts

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ ll
total 45
-rw-r--r-- 1 s2tha 197609 1334 Feb  7 10:29 CheckoutService.class
-rw-r--r-- 1 s2tha 197609  990 Feb  7 10:29 CheckoutServiceMainApplication.class
-rw-r--r-- 1 s2tha 197609 5864 Feb  7 10:31 CheckoutServiceMainApplication.java
-rw-r--r-- 1 s2tha 197609 4074 Feb  7 10:33 CheckoutServiceMainApplication.js
-rw-r--r-- 1 s2tha 197609 3462 Feb  7 10:33 CheckoutServiceMainApplication.ts
-rw-r--r-- 1 s2tha 197609 1099 Feb  7 10:29 PayPalPaymentProcessor.class
-rw-r--r-- 1 s2tha 197609  155 Feb  7 10:29 PaymentProcessor.class
-rw-r--r-- 1 s2tha 197609 1459 Feb  7 10:29 PaymentProcessorFactory.class
-rw-r--r-- 1 s2tha 197609 1039 Feb  7 10:29 PaymentProvider.class
-rw-r--r-- 1 s2tha 197609 1104 Feb  7 10:29 RazorpayPaymentProcessor.class
-rw-r--r-- 1 s2tha 197609 1100 Feb  7 10:29 StripePaymentProcessor.class

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ node CheckoutServiceMainApplication
Starting checkout with provider: STRIPE
Paid ₹1500 using Stripe

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$

==============================
5. SOLID Principles 
==============================

| Principle   | Status | Why                                     |
|------------|--------|------------------------------------------|
| S – SRP    | Y     | Each class has one reason to change      |
| O – OCP    | Y     | New provider without modifying checkout |
| L – LSP    | Y     | All processors interchangeable          |
| I – ISP    | Y     | Small focused interface                 |
| D – DIP    | Y     | Depends on abstraction, not concrete    |

*
*
*/
