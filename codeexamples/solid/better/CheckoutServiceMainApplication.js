/**
 * ===============================
 * 2. Concrete Implementations (SRP)
 * ===============================
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
var RazorpayPaymentProcessor = /** @class */ (function () {
    function RazorpayPaymentProcessor() {
    }
    RazorpayPaymentProcessor.prototype.pay = function (amount) {
        console.info(RazorpayPaymentProcessor.PAYMENT_MESSAGE.replace("%s", amount.toString()));
    };
    RazorpayPaymentProcessor.PAYMENT_MESSAGE = "Paid ₹%s using Razorpay";
    return RazorpayPaymentProcessor;
}());
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
 * ===============================
 * 3. Payment Provider Enum
 * ===============================
 */
var PaymentProvider;
(function (PaymentProvider) {
    PaymentProvider["STRIPE"] = "STRIPE";
    PaymentProvider["RAZORPAY"] = "RAZORPAY";
    PaymentProvider["PAYPAL"] = "PAYPAL";
})(PaymentProvider || (PaymentProvider = {}));
/**
 * ===============================
 * 4. Factory (OCP)
 * ===============================
 */
var PaymentProcessorFactory = /** @class */ (function () {
    function PaymentProcessorFactory(processors) {
        this.processors = processors;
    }
    PaymentProcessorFactory.prototype.get = function (provider) {
        var processor = this.processors[provider];
        if (!processor) {
            throw new Error("Unsupported payment provider: ".concat(provider));
        }
        return processor;
    };
    return PaymentProcessorFactory;
}());
/**
 * ===============================
 * 5. Business Logic (SRP + DIP)
 * ===============================
 */
var CheckoutService = /** @class */ (function () {
    function CheckoutService(factory) {
        this.factory = factory;
    }
    CheckoutService.prototype.checkout = function (amount, provider) {
        if (amount <= 0) {
            throw new Error("Amount must be greater than zero");
        }
        console.info("Starting checkout with provider: ".concat(provider));
        var processor = this.factory.get(provider);
        processor.pay(amount);
    };
    return CheckoutService;
}());
/**
 * ===============================
 * 6. Main Entry Point
 * ===============================
 */
var CheckoutServiceMainApplication = /** @class */ (function () {
    function CheckoutServiceMainApplication() {
    }
    CheckoutServiceMainApplication.main = function () {
        var _a;
        var factory = new PaymentProcessorFactory((_a = {},
            _a[PaymentProvider.STRIPE] = new StripePaymentProcessor(),
            _a[PaymentProvider.RAZORPAY] = new RazorpayPaymentProcessor(),
            _a[PaymentProvider.PAYPAL] = new PayPalPaymentProcessor(),
            _a));
        var checkoutService = new CheckoutService(factory);
        checkoutService.checkout(1500, PaymentProvider.STRIPE);
    };
    return CheckoutServiceMainApplication;
}());
// Run
CheckoutServiceMainApplication.main();
/*

| Principle   | Status | Why                                     |
|------------|--------|------------------------------------------|
| S – SRP    | Y     | Each class has one reason to change      |
| O – OCP    | Y     | New provider without modifying checkout |
| L – LSP    | Y     | All processors interchangeable          |
| I – ISP    | Y     | Small focused interface                 |
| D – DIP    | Y     | Depends on abstraction, not concrete    |

*/
