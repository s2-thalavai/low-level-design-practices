# low-level-design-practices


## Understanding the Concepts of Programming

Most programming languages share a common foundation.  
While the syntax may look different, the **core ideas behind programming remain the same**.

This guide explains those **fundamental concepts**, one step at a time, in a simple and easy-to-understand way.

----------

## What Are These About?

To truly understand programming, it’s important to learn the **basic building blocks** that every program is made of.

These pages focus on:

-   Core programming concepts explained clearly
    
-   Practical code examples
    
-   Visual explanations using images and simple illustrations
    
-   Step-by-step learning to build strong fundamentals
    

-------------


## What Is Programming?

Programming is the process of **telling a computer what to do**.

When you become good at programming, you gain the ability to:

-   Solve problems
    
-   Automate tasks
    
-   Build applications
    
-   Make computers work exactly the way you want
    

----------

## Getting Started With Programming

To become confident in programming, it’s important to learn the **core concepts first**.

The good news is:

> Most programming languages use the same fundamental concepts — only the syntax changes.

### The First 5 Core Concepts

Start with these concepts in this order:

1.  **Variables** – storing data
    
2.  **If Statements** – making decisions
    
3.  **Arrays** – storing multiple values
    
4.  **Loops** – repeating actions
    
5.  **Functions** – organizing and reusing code
    

Learning them step by step helps you build a strong foundation.

----------

## Supporting Basics You Should Know

To fully understand the core concepts, you should also be familiar with:

-   **Data Types** (numbers, text, true/false, etc.)
    
-   **Boolean Logic** (true, false, and conditions)
    
-   **Operators** (such as +, -, ==, >, &&)
    

Once you understand these basics, you’ll be ready to move on to more advanced topics.

----------

## Programming Languages

A **programming language** is the tool we use to write instructions for a computer.

Different languages are designed for different purposes:

-   **JavaScript** – Web development
    
-   **Python** – AI, data science, automation
    
-   **Java / Kotlin** – Enterprise applications
    
-   **C / C++** – Embedded systems and microcontrollers
    

While the **concepts remain the same**, the **syntax** (how you write the code) differs from one language to another.

----------

## Same Concept, Different Syntax

For example, here is how a loop that counts down from 10 looks in different languages:

**Python**

```py
for i in range(10, 0, -1):
  print(i)
print('Liftoff!')
``` 

**JavaScript**

```js
for (let i = 10; i > 0; i--) {
  console.log(i);
}
console.log('Liftoff!');
``` 

**Java**

```java
for (int i = 10; i > 0; i--) {
  System.out.println(i);
}
System.out.println("Liftoff!");
``` 

**C++**

```cpp
for (int i = 10; i > 0; i--) {
  cout << to_string(i) + "\\n";
}
cout << "Liftoff!\\n
``` 

Even though the syntax looks different, the **logic is exactly the same**.

--------------------

By learning the concepts first, you’ll be able to understand **any programming language more easily**.

LLD Practices using

1. Java
2. Python
3. Typescript


---------------

## Payment Interface from Checkout Service

At a high level, this is a checkout system that supports multiple payment providers.


### Flow

1.  Client calls `CheckoutService.checkout(amount, provider)`
    
2.  Based on the `provider` string:
    
    -   Stripe → `StripePaymentProcessor`
        
    -   Razorpay → `RazorpayPaymentProcessor`
        
    -   PayPal → `PayPalPaymentProcessor`
        
3.  The selected processor logs a payment message.
    

So **functionally**, it works fine:

```
Checkout → choose provider → pay → log
```


---------------

## GST / VAT tax engine

A real GST/VAT engine must handle:

-   Multiple tax regimes (GST, VAT, Sales Tax)
    
-   Split taxes (CGST / SGST / IGST)
    
-   Zero / exempt / reduced rates
    
-   Per-line tax calculation
    
-   Rounding rules
    
-   Tax jurisdiction rules
    
-   Future changes without rewriting invoices

---------------


## GST e-invoice JSON generation

-   What GST e-Invoice actually is
    
-   Architectural placement (Clean / Hexagonal)
    
-   Canonical → NIC JSON mapping
    
-   JSON builder pipeline
    
-   Sample **NIC-compliant JSON**
    
-   Validation & IRN flow



---------------

## Credit notes & invoice amendments


-   GST e-Invoice (NIC JSON schema)
    
-   Digital signing & hashing
    
-   Ledger & accounting entries
    
-   Payment reconciliation
    
-   Azure microservice deployment
    
-   LLM-based audit & anomaly detection
  
---------------

## PDF generation pipeline


-   PDF signing & hashing
    
-   S3 / Blob archival
    
-   GST e-Invoice JSON (NIC schema)
    
-   Credit note PDFs
    
-   Azure deployment
    
-   LLM-based invoice QA
  
---------------
