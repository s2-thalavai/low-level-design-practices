# Gateway Aggregator Pattern

We’ll assume:

-   Vendor uploads invoice
    
-   Gateway calls:
    
    -   Invoice Processing Service (basic validation + persistence)
        
    -   PO Service
        
    -   Vendor Service
        
-   Gateway aggregates responses and returns a unified response
    

Using:

-   Spring WebFlux
    
-   Spring Cloud Gateway
    

----------

## Updated Architecture (Gateway Aggregator)


## Aggregated Response Model

```java
public  record  InvoiceUploadAggregatedResponse(
        InvoiceResponse invoice,
        PurchaseOrder po,
        Vendor vendor
) {}
``` 

----------

## Gateway Aggregator Controller (WebFlux)

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vendor")
public class InvoiceGatewayController {

    private final WebClient webClient;

    @PostMapping("/invoice")
    public Mono<InvoiceUploadAggregatedResponse> uploadInvoice(
            @RequestBody InvoiceUploadRequest request) {

        // 1️. Call Invoice Processing Service
        Mono<InvoiceResponse> invoiceMono = webClient.post()
                .uri("http://invoice-service/invoices")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InvoiceResponse.class);

        // 2️ Call PO Service
        Mono<PurchaseOrder> poMono = webClient.get()
                .uri("http://po-service/po/{id}", request.getPoId())
                .retrieve()
                .bodyToMono(PurchaseOrder.class);

        // 3️ Call Vendor Service
        Mono<Vendor> vendorMono = webClient.get()
                .uri("http://vendor-service/vendors/{id}", request.getVendorId())
                .retrieve()
                .bodyToMono(Vendor.class);

        // 4️. Aggregate in Gateway
        return Mono.zip(invoiceMono, poMono, vendorMono)
                .map(tuple -> new InvoiceUploadAggregatedResponse(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3()
                ));
    }
}

``` 

----------

# 🛡 Add Production Resilience at Gateway

You should protect downstream calls.

### Timeout + Retry Example

```java
private Mono<PurchaseOrder> fetchPo(String poId) {
    return webClient.get()
            .uri("http://po-service/po/{id}", poId)
            .retrieve()
            .bodyToMono(PurchaseOrder.class)
            .timeout(Duration.ofSeconds(2))
            .retryWhen(Retry.backoff(2, Duration.ofMillis(200)));
}

```


Gateway Aggregator is fine when:

 Aggregation is mostly read-oriented  
 Minimal business logic  
 No distributed transaction  
 No complex compensation

For invoice upload, it is acceptable **if**:

-   Invoice Service handles validation and persistence
    
-   Gateway only enriches response
    
-   No transactional dependency between PO & Vendor fetch


| Layer           | Responsibility        |
| --------------- | --------------------- |
| Gateway         | Routing + aggregation |
| Invoice Service | Business validation   |
| PO Service      | PO data ownership     |
| Vendor Service  | Vendor data ownership |



Gateway Aggregator = View composition  
Orchestrator = Business workflow coordination


In your current Gateway Aggregator example, we used `Mono.zip()` because:

-   Uploading **one invoice**
    
-   Fetching **one PO**
    
-   Fetching **one Vendor**
    

That’s naturally a `Mono` case.

But if you want to use **`Flux`**, that usually means:

> You are processing multiple invoices  
> OR aggregating multiple downstream results

Let’s convert this into a **bulk invoice upload scenario**.


## Scenario: Vendor Uploads Multiple Invoices

Vendor uploads:

```java
[
  { "poId": "PO-1", "vendorId": "V1" },
  { "poId": "PO-2", "vendorId": "V1" },
  { "poId": "PO-3", "vendorId": "V2" }
]

``` 

Now:

-   For each invoice
    
-   Call Invoice Service
    
-   Call PO Service
    
-   Call Vendor Service
    
-   Aggregate per invoice
    
-   Return a Flux stream
    

This is where `Flux` shines.


## Gateway Aggregator Using Flux

- Very important in bulk scenarios.

- Without control, 1,000 invoices → 3,000 HTTP calls instantly.

- Add concurrency limit:


```java
@PostMapping("/bulk-invoice")
public Flux<InvoiceUploadAggregatedResponse> uploadBulkInvoices(
        @RequestBody Flux<InvoiceUploadRequest> requests) {

    return requests
            .flatMap(this::aggregateInvoice, 10); // max 10 concurrent invoices
}
```


Now:

-   Backpressure respected
    
-   System protected
    
-   No downstream overload


## If PO Service Returns Multiple Line Items (True Flux Case)

Suppose PO Service returns multiple line items:


```java
Flux<PoLineItem> poItemsFlux = webClient.get()
        .uri("http://po-service/po/{id}/items", request.getPoId())
        .retrieve()
        .bodyToFlux(PoLineItem.class);

```
Now aggregation becomes:

```java
return poItemsFlux
        .collectList()
        .zipWith(invoiceMono)
        .zipWith(vendorMono)
        .map(tuple -> {
            List<PoLineItem> items = tuple.getT1().getT1();
            InvoiceResponse invoice = tuple.getT1().getT2();
            Vendor vendor = tuple.getT2();

            return new InvoiceUploadAggregatedResponse(invoice, items, vendor);
        });
```

---------------


## Aggregation Logic per Invoice


```java
private Mono<InvoiceUploadAggregatedResponse> aggregateInvoice(
        InvoiceUploadRequest request) {

    Mono<InvoiceResponse> invoiceMono = webClient.post()
            .uri("http://invoice-service/invoices")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(InvoiceResponse.class);

    Mono<PurchaseOrder> poMono = webClient.get()
            .uri("http://po-service/po/{id}", request.getPoId())
            .retrieve()
            .bodyToMono(PurchaseOrder.class);

    Mono<Vendor> vendorMono = webClient.get()
            .uri("http://vendor-service/vendors/{id}", request.getVendorId())
            .retrieve()
            .bodyToMono(Vendor.class);

    return Mono.zip(invoiceMono, poMono, vendorMono)
            .map(tuple -> new InvoiceUploadAggregatedResponse(
                    tuple.getT1(),
                    tuple.getT2(),
                    tuple.getT3()
            ));
}

```

## What’s Happening Reactively?

```
Flux<InvoiceUploadRequest>
        │
        ▼
flatMap (parallel execution)
        │
        ▼
Mono.zip (per invoice aggregation)
        │
        ▼
Flux<InvoiceUploadAggregatedResponse>

```



Each invoice:

-   Executes in parallel
    
-   Non-blocking
    
-   Independent pipeline


| Use Mono         | Use Flux          |
| ---------------- | ----------------- |
| Single request   | Bulk processing   |
| Single response  | Streaming results |
| One-to-one calls | One-to-many calls |
| CRUD endpoint    | Batch endpoint    |



## Note to remembetr

Using Flux in Gateway Aggregator is powerful for:

-   Bulk invoice uploads
    
-   Streaming dashboards
    
-   Live reconciliation views
    
-   Large dataset aggregation
    

But always:

- Limit concurrency  
- Add timeout  
- Add retry carefully  
- Avoid unbounded flatMap

----------------------

## Scatter- gather Design Pattern

