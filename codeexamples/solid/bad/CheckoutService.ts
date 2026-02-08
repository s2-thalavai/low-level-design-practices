/**
 * Stripe payment implementation.
 * Each payment provider exposes its own concrete API.
 */
class StripePaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using Stripe";

  public pay(amount: number): void {
    console.info(
      StripePaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

/**
 * Razorpay payment implementation.
 */
class RazorpayPaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using Razorpay";

  public pay(amount: number): void {
    console.info(
      RazorpayPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

/**
 * PayPal payment implementation.
 */
class PayPalPaymentProcessor {
  private static readonly PAYMENT_MESSAGE = "Paid ₹%s using PayPal";

  public pay(amount: number): void {
    console.info(
      PayPalPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString())
    );
  }
}

/**
 * CheckoutService contains core business logic.
 *
 * PROBLEM:
 * This class directly depends on concrete payment providers.
 * Any change in payment gateways forces a change here.
 */
class CheckoutService {
  /**
   * Executes checkout using a specific payment provider.
   *
   * @param amount amount to be paid
   * @param provider payment provider name
   */
  // Issue 3: String-based provider selection (fragile & unsafe)
  public checkout(amount: number, provider: string): void {
    console.info(`Starting checkout with provider: ${provider}`);

    // Issue 2: Violates Open/Closed Principle (OCP)
    if (provider.toUpperCase() === "STRIPE") {
      // Issue 1: Tight coupling to concrete classes (Dependency Inversion violated)
      const stripe = new StripePaymentProcessor();
      stripe.pay(amount);
    } else if (provider.toUpperCase() === "RAZORPAY") {
      const razorpay = new RazorpayPaymentProcessor();
      razorpay.pay(amount);
    } else if (provider.toUpperCase() === "PAYPAL") {
      const paypal = new PayPalPaymentProcessor();
      paypal.pay(amount);
    } else {
      console.error(`Unsupported payment provider: ${provider}`);
      throw new Error("Unsupported payment provider");
    }
  }
}

/**
 * Main application entry point.
 */
class MainApplication {
  public static main(): void {
    const checkoutService = new CheckoutService();

    checkoutService.checkout(500, "STRIPE");
    checkoutService.checkout(750, "RAZORPAY");
    checkoutService.checkout(1200, "PAYPAL");
  }
}

// Simulate Java-style main execution
MainApplication.main();

/*


s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ ll
total 16
-rw-r--r-- 1 s2tha 197609 5491 Feb  7 08:47 CheckoutService.java
-rw-r--r-- 1 s2tha 197609 2467 Feb  6 20:42 CheckoutService.py
-rw-r--r-- 1 s2tha 197609 2625 Feb  7 08:50 CheckoutService.ts

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ tsc CheckoutService.ts

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ ll
total 20
-rw-r--r-- 1 s2tha 197609 5491 Feb  7 08:47 CheckoutService.java
-rw-r--r-- 1 s2tha 197609 3297 Feb  7 08:51 CheckoutService.js
-rw-r--r-- 1 s2tha 197609 2467 Feb  6 20:42 CheckoutService.py
-rw-r--r-- 1 s2tha 197609 2625 Feb  7 08:50 CheckoutService.ts

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ node CheckoutService.js
Starting checkout with provider: STRIPE
Paid ₹500 using Stripe
Starting checkout with provider: RAZORPAY
Paid ₹750 using Razorpay
Starting checkout with provider: PAYPAL
Paid ₹1200 using PayPal

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)

*/ 