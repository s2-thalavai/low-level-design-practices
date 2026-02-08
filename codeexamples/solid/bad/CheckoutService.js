/**
 * Stripe payment implementation.
 * Each payment provider exposes its own concrete API.
 */
var StripePaymentProcessor = /** @class */ (function () {
    function StripePaymentProcessor() {
    }
    StripePaymentProcessor.prototype.pay = function (amount) {
        console.info(StripePaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString()));
    };
    StripePaymentProcessor.PAYMENT_MESSAGE = "Paid ₹%s using Stripe";
    return StripePaymentProcessor;
}());
/**
 * Razorpay payment implementation.
 */
var RazorpayPaymentProcessor = /** @class */ (function () {
    function RazorpayPaymentProcessor() {
    }
    RazorpayPaymentProcessor.prototype.pay = function (amount) {
        console.info(RazorpayPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString()));
    };
    RazorpayPaymentProcessor.PAYMENT_MESSAGE = "Paid ₹%s using Razorpay";
    return RazorpayPaymentProcessor;
}());
/**
 * PayPal payment implementation.
 */
var PayPalPaymentProcessor = /** @class */ (function () {
    function PayPalPaymentProcessor() {
    }
    PayPalPaymentProcessor.prototype.pay = function (amount) {
        console.info(PayPalPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString()));
    };
    PayPalPaymentProcessor.PAYMENT_MESSAGE = "Paid ₹%s using PayPal";
    return PayPalPaymentProcessor;
}());
/**
 * CheckoutService contains core business logic.
 *
 * PROBLEM:
 * This class directly depends on concrete payment providers.
 * Any change in payment gateways forces a change here.
 */
var CheckoutService = /** @class */ (function () {
    function CheckoutService() {
    }
    /**
     * Executes checkout using a specific payment provider.
     *
     * @param amount amount to be paid
     * @param provider payment provider name
     */
    // Issue 3: String-based provider selection (fragile & unsafe)
    CheckoutService.prototype.checkout = function (amount, provider) {
        console.info("Starting checkout with provider: ".concat(provider));
        // Issue 2: Violates Open/Closed Principle (OCP)
        if (provider.toUpperCase() === "STRIPE") {
            // Issue 1: Tight coupling to concrete classes (Dependency Inversion violated)
            var stripe = new StripePaymentProcessor();
            stripe.pay(amount);
        }
        else if (provider.toUpperCase() === "RAZORPAY") {
            var razorpay = new RazorpayPaymentProcessor();
            razorpay.pay(amount);
        }
        else if (provider.toUpperCase() === "PAYPAL") {
            var paypal = new PayPalPaymentProcessor();
            paypal.pay(amount);
        }
        else {
            console.error("Unsupported payment provider: ".concat(provider));
            throw new Error("Unsupported payment provider");
        }
    };
    return CheckoutService;
}());
/**
 * Main application entry point.
 */
var MainApplication = /** @class */ (function () {
    function MainApplication() {
    }
    MainApplication.main = function () {
        var checkoutService = new CheckoutService();
        checkoutService.checkout(500, "STRIPE");
        checkoutService.checkout(750, "RAZORPAY");
        checkoutService.checkout(1200, "PAYPAL");
    };
    return MainApplication;
}());
// Simulate Java-style main execution
MainApplication.main();
