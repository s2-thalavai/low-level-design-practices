import sys
import logging

sys.stdout.reconfigure(encoding="utf-8")

logging.basicConfig(level=logging.INFO, encoding="utf-8")
logging.getLogger("test").info("Paid $750.0 using Razorpay")

class StripePaymentProcessor:
    """
    Stripe payment implementation.
    Each payment provider exposes its own concrete API.
    """

    logger = logging.getLogger("StripePaymentProcessor")
    PAYMENT_MESSAGE = "Paid $%s using Stripe"

    def pay(self, amount: float) -> None:
        self.logger.info(self.PAYMENT_MESSAGE, amount)


class RazorpayPaymentProcessor:
    """
    Razorpay payment implementation.
    """

    logger = logging.getLogger("RazorpayPaymentProcessor")
    PAYMENT_MESSAGE = "Paid $%s using Razorpay"

    def pay(self, amount: float) -> None:
        self.logger.info(self.PAYMENT_MESSAGE, amount)


class PayPalPaymentProcessor:
    """
    PayPal payment implementation.
    """

    logger = logging.getLogger("PayPalPaymentProcessor")
    PAYMENT_MESSAGE = "Paid $%s using PayPal"

    def pay(self, amount: float) -> None:
        self.logger.info(self.PAYMENT_MESSAGE, amount)


class CheckoutService:
    """
    CheckoutService contains core business logic.

    PROBLEM:
    This class directly depends on concrete payment providers.
    Any change in payment gateways forces a change here.
    """

    logger = logging.getLogger("CheckoutService")

    def checkout(self, amount: float, provider: str) -> None:
        """
        Executes checkout using a specific payment provider.

        Issue 3: String-based provider selection (fragile & unsafe)
        """

        self.logger.info("Starting checkout with provider: %s", provider)

        # Issue 2: Violates Open/Closed Principle (OCP)
        if provider.upper() == "STRIPE":

            # Issue 1: Tight coupling to concrete classes (Dependency Inversion violated)
            stripe = StripePaymentProcessor()
            stripe.pay(amount)

        elif provider.upper() == "RAZORPAY":

            razorpay = RazorpayPaymentProcessor()
            razorpay.pay(amount)

        elif provider.upper() == "PAYPAL":

            paypal = PayPalPaymentProcessor()
            paypal.pay(amount)

        else:
            self.logger.error("Unsupported payment provider: %s", provider)
            raise ValueError("Unsupported payment provider")


def main() -> None:
    """
    Application entry point.
    """
    checkout_service = CheckoutService()

    checkout_service.checkout(1000.0, "STRIPE")
    checkout_service.checkout(750.0, "RAZORPAY")
    checkout_service.checkout(500.0, "PAYPAL")

    # failure case
    try:
        checkout_service.checkout(200.0, "PHONEPE")
    except ValueError as e:
        print(f"Checkout failed: {e}")


if __name__ == "__main__":
    main()


"""
-------------------------
Execution Output:
-------------------------

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ python3 CheckoutService.py
INFO:CheckoutService:Starting checkout with provider: STRIPE
INFO:StripePaymentProcessor:Paid $1000.0 using Stripe
INFO:CheckoutService:Starting checkout with provider: RAZORPAY
INFO:RazorpayPaymentProcessor:Paid $750.0 using Razorpay
INFO:CheckoutService:Starting checkout with provider: PAYPAL
INFO:PayPalPaymentProcessor:Paid $500.0 using PayPal

-------------------------
Execution Output: (without try-except block)
-------------------------

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ python3 CheckoutService.py
INFO:CheckoutService:Starting checkout with provider: STRIPE
INFO:StripePaymentProcessor:Paid $1000.0 using Stripe
INFO:CheckoutService:Starting checkout with provider: RAZORPAY
INFO:RazorpayPaymentProcessor:Paid $750.0 using Razorpay
INFO:CheckoutService:Starting checkout with provider: PAYPAL
INFO:PayPalPaymentProcessor:Paid $500.0 using PayPal
INFO:CheckoutService:Starting checkout with provider: PHONEPE
ERROR:CheckoutService:Unsupported payment provider: PHONEPE
Traceback (most recent call last):
  File "D:\git\low-level-design-practices\codeexamples\solid\bad\CheckoutService.py", line 100, in <module>
    main()
    ~~~~^^
  File "D:\git\low-level-design-practices\codeexamples\solid\bad\CheckoutService.py", line 96, in main
    checkout_service.checkout(200.0, "PHONEPE")
    ~~~~~~~~~~~~~~~~~~~~~~~~~^^^^^^^^^^^^^^^^^^
  File "D:\git\low-level-design-practices\codeexamples\solid\bad\CheckoutService.py", line 82, in checkout
    raise ValueError("Unsupported payment provider")
ValueError: Unsupported payment provider

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$


-------------------------
Execution Output: (with try-except block)
-------------------------

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$ python3 CheckoutService.py
D:\git\low-level-design-practices\codeexamples\solid\bad\CheckoutService.py:136: SyntaxWarning: "\g" is an invalid escape sequence. Such sequences will not work in the future. Did you mean "\\g"? A raw string is also an option.
File "D:\git\low-level-design-practices\codeexamples\solid\bad\CheckoutService.py", line 100, in <module>

INFO:CheckoutService:Starting checkout with provider: STRIPE
INFO:StripePaymentProcessor:Paid $1000.0 using Stripe

INFO:CheckoutService:Starting checkout with provider: RAZORPAY
INFO:RazorpayPaymentProcessor:Paid $750.0 using Razorpay

INFO:CheckoutService:Starting checkout with provider: PAYPAL
INFO:PayPalPaymentProcessor:Paid $500.0 using PayPal

INFO:CheckoutService:Starting checkout with provider: PHONEPE
ERROR:CheckoutService:Unsupported payment provider: PHONEPE
Checkout failed: Unsupported payment provider

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/bad (main)
$

"""