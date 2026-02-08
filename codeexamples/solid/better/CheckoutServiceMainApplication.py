# package better;

from abc import ABC, abstractmethod
from enum import Enum
from typing import Dict
import logging

# ===============================
# Logging Configuration
# ===============================
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

"""
===============================
1. Abstraction (DIP)
===============================
"""
class PaymentProcessor(ABC):

    @abstractmethod
    def pay(self, amount: float) -> None:
        pass


"""
===============================
2. Concrete Implementations (SRP)
===============================
"""
class StripePaymentProcessor(PaymentProcessor):

    logger = logging.getLogger("StripePaymentProcessor")
    PAYMENT_MESSAGE = "Paid $ %s using Stripe"

    def pay(self, amount: float) -> None:
        if self.logger.isEnabledFor(logging.INFO):
            self.logger.info(self.PAYMENT_MESSAGE % amount)


class RazorpayPaymentProcessor(PaymentProcessor):

    logger = logging.getLogger("RazorpayPaymentProcessor")
    PAYMENT_MESSAGE = "Paid $ %s using Razorpay"

    def pay(self, amount: float) -> None:
        if self.logger.isEnabledFor(logging.INFO):
            self.logger.info(self.PAYMENT_MESSAGE % amount)


class PayPalPaymentProcessor(PaymentProcessor):

    logger = logging.getLogger("PayPalPaymentProcessor")
    PAYMENT_MESSAGE = "Paid $%s using PayPal"

    def pay(self, amount: float) -> None:
        if self.logger.isEnabledFor(logging.INFO):
            self.logger.info(self.PAYMENT_MESSAGE % amount)


class PaymentProvider(Enum):
    STRIPE = "STRIPE"
    RAZORPAY = "RAZORPAY"
    PAYPAL = "PAYPAL"


"""
===============================
3. Factory (OCP)
===============================
"""
class PaymentProcessorFactory:

    def __init__(self, processors: Dict[PaymentProvider, PaymentProcessor]):
        self.processors = processors

    def get(self, provider: PaymentProvider) -> PaymentProcessor:

        if provider is None:
            raise ValueError("Payment provider must be specified")

        processor = self.processors.get(provider)

        if processor is None:
            raise ValueError(f"Unsupported payment provider: {provider}")

        return processor


"""
===============================
4. Business Logic (SRP + DIP)
===============================
"""
class CheckoutService:

    logger = logging.getLogger("CheckoutService")

    def __init__(self, factory: PaymentProcessorFactory):
        self.factory = factory

    """
    Executes checkout using selected payment provider.

    :param amount: payment amount (must be > 0)
    :param provider: payment provider name
    """
    def checkout(self, amount: float, provider: PaymentProvider) -> None:

        if amount <= 0:
            raise ValueError("Amount must be greater than zero")

        if self.logger.isEnabledFor(logging.INFO):
            self.logger.info("Starting checkout with provider: %s", provider.name)

        processor = self.factory.get(provider)
        processor.pay(amount)


class CheckoutServiceMainApplication:

    @staticmethod
    def main():
        factory = PaymentProcessorFactory({
            PaymentProvider.STRIPE: StripePaymentProcessor(),
            PaymentProvider.RAZORPAY: RazorpayPaymentProcessor(),
            PaymentProvider.PAYPAL: PayPalPaymentProcessor()
        })

        checkout_service = CheckoutService(factory)
        checkout_service.checkout(1500.0, PaymentProvider.STRIPE)


if __name__ == "__main__":
    CheckoutServiceMainApplication.main()


"""
===============================
Output
===============================

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$ python3 CheckoutServiceMainApplication.py
INFO:CheckoutService:Starting checkout with provider: STRIPE
INFO:StripePaymentProcessor:Paid $ 1500.0 using Stripe

s2tha@thalasi-windows MINGW64 /d/git/low-level-design-practices/codeexamples/solid/better (main)
$

===============================
5. SOLID Principles
===============================

| Principle | Status | Why |
| ----------- | ------ | --------------------------------------- |
| S – SRP | Y | Checkout ≠ provider creation |
| O – OCP | Y | Add provider without modifying Checkout |
| L – LSP | Y | All processors interchangeable |
| I – ISP | Y | Small focused interface |
| D – DIP | Y | Depends on abstraction |
"""