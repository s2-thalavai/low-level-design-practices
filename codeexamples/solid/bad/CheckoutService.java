/**

This code is intentionally written as a “bad design” example to highlight SOLID violations.

At a high level, this is a **checkout system** that supports multiple payment providers.

### Flow

1.  Client calls `CheckoutService.checkout(amount, provider)`
    
2.  Based on the `provider` string:
    
    -   Stripe → `StripePaymentProcessor`
        
    -   Razorpay → `RazorpayPaymentProcessor`
        
    -   PayPal → `PayPalPaymentProcessor`
        
3.  The selected processor logs a payment message.    

So **functionally**, it works fine:

`Checkout → choose provider → pay → log`

*/

package bad;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stripe payment implementation.
 * Each payment provider exposes its own concrete API.
 */
class StripePaymentProcessor {

    private static final Logger logger = Logger.getLogger(StripePaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid ₹%s using Stripe";

    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

/**
 * Razorpay payment implementation.
 */
class RazorpayPaymentProcessor {

    private static final Logger logger = Logger.getLogger(RazorpayPaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid ₹%s using Razorpay";

    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

/**
 * PayPal payment implementation.
 */
class PayPalPaymentProcessor {

    private static final Logger logger = Logger.getLogger(PayPalPaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid ₹%s using PayPal";

    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

/**
 * CheckoutService contains core business logic.
 *
 * PROBLEM:
 * This class directly depends on concrete payment providers.
 * Any change in payment gateways forces a change here.
 */
public class CheckoutService {

    private static final Logger logger = Logger.getLogger(CheckoutService.class.getName());

    /**
     * Executes checkout using a specific payment provider.
     *
     * @param amount   amount to be paid
     * @param provider payment provider name
     */
    // Issue 3: String-based provider selection (fragile & unsafe)
    public void checkout(double amount, String provider) {

        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Starting checkout with provider: %s", provider));
        }

        // Issue 2: Violates Open/Closed Principle (OCP)
        if ("STRIPE".equalsIgnoreCase(provider)) {

            // issue 1: Tight coupling to concrete classes (Dependency Inversion violated)
            StripePaymentProcessor stripe = new StripePaymentProcessor();
            stripe.pay(amount);

        } else if ("RAZORPAY".equalsIgnoreCase(provider)) {

            RazorpayPaymentProcessor razorpay = new RazorpayPaymentProcessor();
            razorpay.pay(amount);

        } else if ("PAYPAL".equalsIgnoreCase(provider)) {

            PayPalPaymentProcessor paypal = new PayPalPaymentProcessor();
            paypal.pay(amount);

        } else {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.severe(String.format("Unsupported payment provider: %s", provider));
            }
            throw new IllegalArgumentException("Unsupported payment provider");
        }
    }

    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService();

        checkoutService.checkout(500, "STRIPE");
        checkoutService.checkout(750, "RAZORPAY");
        checkoutService.checkout(1200, "PAYPAL");
    }
}

/**

## Design problems

### Issue 1: Tight coupling (Dependency Inversion violated)

`StripePaymentProcessor  stripe  =  new  StripePaymentProcessor();` 

-   `CheckoutService` directly depends on **concrete classes**
    
-   High-level module (`CheckoutService`) depends on low-level modules (`StripePaymentProcessor`)
    
-   Violates **Dependency Inversion Principle (DIP)**
    

**Impact**

-   Cannot mock payment processors easily
    
-   Cannot swap implementations
    
-   Hard to test
    

----------

###  Issue 2: Violates Open/Closed Principle (OCP)

`else  if ("PAYPAL".equalsIgnoreCase(provider)) { PayPalPaymentProcessor  paypal  =  new  PayPalPaymentProcessor();
}` 

If tomorrow you add **PhonePe** or **UPI**:

-   You must modify `CheckoutService`
    
-   That means the class is **not closed for modification**
    

**Impact**

-   Every new provider = code change + retest
    
-   High regression risk
    
-   Poor scalability
    

----------

### Issue 3: String-based provider selection (fragile)

`public  void  checkout(double amount, String provider)` 

Problems with this:

-   `"STRIPE"` vs `"stripe"` vs `"Stripe"`
    
-   Typos compile fine, fail at runtime
    
-   No type safety
    

**Impact**

-   Runtime errors
    
-   Harder refactoring
    
-   Poor IDE support
    

----------

###  Issue 4: Business logic + orchestration mixed

`CheckoutService` is doing **too much**:

-   Deciding _which_ payment provider to use
    
-   Creating objects
    
-   Executing payments
    

Violates **Single Responsibility Principle (SRP)**.
*/
