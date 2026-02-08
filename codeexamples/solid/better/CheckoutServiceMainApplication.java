package better;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ===============================
 * 1. Abstraction (DIP)
 * ===============================
 */
interface PaymentProcessor {
    void pay(double amount);
}

/**
 * ===============================
 * 2. Concrete Implementations (SRP)
 * ===============================
 */
class StripePaymentProcessor implements PaymentProcessor {

    private static final Logger logger = Logger.getLogger(StripePaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid $ %s using Stripe";

    @Override
    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

class RazorpayPaymentProcessor implements PaymentProcessor {

    private static final Logger logger = Logger.getLogger(RazorpayPaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid $ %s using Razorpay";

    @Override
    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

class PayPalPaymentProcessor implements PaymentProcessor {

    private static final Logger logger = Logger.getLogger(PayPalPaymentProcessor.class.getName());
    private static final String PAYMENT_MESSAGE = "Paid $%s using PayPal";

    @Override
    public void pay(double amount) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(PAYMENT_MESSAGE, amount));
        }
    }
}

enum PaymentProvider {
    STRIPE,
    RAZORPAY,
    PAYPAL
}

/**
 * ===============================
 * 3. Factory (OCP)
 * ===============================
 */
class PaymentProcessorFactory {

    private final Map<PaymentProvider, PaymentProcessor> processors;

    public PaymentProcessorFactory(Map<PaymentProvider, PaymentProcessor> processors) {
        this.processors = processors;
    }

    public PaymentProcessor get(PaymentProvider provider) {

        if (provider == null) {
            throw new IllegalArgumentException("Payment provider must be specified");
        }

        PaymentProcessor processor = processors.get(provider);

        if (processor == null) {
            throw new IllegalArgumentException(
                    "Unsupported payment provider: " + provider);
        }

        return processor;
    }
}

/**
 * ===============================
 * 4. Business Logic (SRP + DIP)
 * ===============================
 */
class CheckoutService {

    private static final Logger logger = Logger.getLogger(CheckoutService.class.getName());

    private final PaymentProcessorFactory factory;

    public CheckoutService(PaymentProcessorFactory factory) {
        this.factory = factory;
    }

    /**
     * Executes checkout using selected payment provider.
     *
     * @param amount   payment amount (must be > 0)
     * @param provider payment provider name
     */
    public void checkout(double amount, PaymentProvider provider) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Starting checkout with provider: {0}", provider);
        }

        PaymentProcessor processor = factory.get(provider);
        processor.pay(amount);
    }
}

public class CheckoutServiceMainApplication {
    public static void main(String[] args) {
        PaymentProcessorFactory factory = new PaymentProcessorFactory(
                Map.of(
                        PaymentProvider.STRIPE, new StripePaymentProcessor(),
                        PaymentProvider.RAZORPAY, new RazorpayPaymentProcessor(),
                        PaymentProvider.PAYPAL, new PayPalPaymentProcessor()));

        CheckoutService checkoutService = new CheckoutService(factory);

        checkoutService.checkout(1500.0, PaymentProvider.STRIPE);
    }
}

/*
 * ===============================
 * Output    
 * ===============================
 * 
s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ java -cp .. better.CheckoutService
Feb 07, 2026 10:18:14 AM better.CheckoutService checkout
INFO: Starting checkout with provider: STRIPE
Feb 07, 2026 10:18:14 AM better.StripePaymentProcessor pay
INFO: Paid ?1500.0 using Stripe

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ javac CheckoutServiceMainApplication.java

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ javac CheckoutServiceMainApplication.java

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ java -cp .. better.CheckoutServiceMainApplication
Feb 07, 2026 10:30:29 AM better.CheckoutService checkout
INFO: Starting checkout with provider: STRIPE
Feb 07, 2026 10:30:29 AM better.StripePaymentProcessor pay
INFO: Paid $ 1500.0 using Stripe

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$

==============================
5. SOLID Principles 
==============================

 * 
 * | Principle | Status | Why |
 * | ----------- | ------ | --------------------------------------- |
 * | **S – SRP** | Y | Checkout ≠ provider creation |
 * | **O – OCP** | Y | Add provider without modifying Checkout |
 * | **L – LSP** | Y | All processors interchangeable |
 * | **I – ISP** | Y | Small focused interface |
 * | **D – DIP** | Y | Depends on abstraction |
 * 
 */