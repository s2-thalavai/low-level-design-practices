
# Case Study Scenario

### System:

-   Spring Boot REST API
    
-   Deployed in Kubernetes
    
-   8 vCPU, 16GB RAM pod
    
-   PostgreSQL database
    
-   Calls 2 downstream REST services
    
-   Traffic: 1,500 requests/sec peak
    

----------

# Problem Observed

During peak load:

-   CPU at 40% (not saturated)
    
-   Response time > 5 seconds
    
-   Thread count ~ 300
    
-   Many threads in `WAITING` state
    
-   DB connection pool exhausted

-----------


# Step 1: Identify Bottleneck

Thread dump shows:

"http-nio-8080-exec-143" WAITING on JDBC connection

>  Threads waiting for DB connections.

# Root Cause

### Default Spring Boot settings:

### 1. Tomcat thread pool

maxThreads = 200

### 2. HikariCP (DB pool)

maximumPoolSize = 10

> 200 request threads competing for 10 DB connections.

Result:

-   190 threads waiting
    
-   Increased latency
    
-   Context switching overhead

------


# Step 2: Fix Strategy

## ✔ Rule: Thread pool size must align with DB pool size

----------

# Tuning Changes

## 1. Reduce Tomcat threads

server:  
 tomcat:  
 threads:  
 max: 50

Why?  
We don’t need 200 concurrent request threads if DB pool is 20.

----------

## 2. Increase HikariCP pool

```yml
spring:  
 datasource:  
 hikari:  
 maximum-pool-size: 30
```

Now:

-   50 request threads
    
-   30 DB connections
    
-   Better balance
    

----------

# Step 3: Async Calls to Downstream Services

Original code:

```java
String  response1  =  restTemplate.getForObject(...);  
String  response2  =  restTemplate.getForObject(...);
```

Sequential blocking.

----------

## Improved Version (Parallel using CompletableFuture)

```java
@Async  
public  CompletableFuture<String> callService1() {  
  return  CompletableFuture.completedFuture(  
  restTemplate.getForObject(...));  
}
```

Now both calls execute in parallel.

----------

# Step 4: Configure Async Thread Pool

```java
@Bean  
public  Executor  taskExecutor() {  
  ThreadPoolTaskExecutor  executor  =  new  ThreadPoolTaskExecutor();  
  executor.setCorePoolSize(20);  
  executor.setMaxPoolSize(40);  
  executor.setQueueCapacity(100);  
  executor.initialize();  
  return  executor;  
}
```

-----------

| Metric            | Before            | After           |
| ----------------- | ----------------- | --------------- |
| CPU usage         | 40%               | 65%             |
| Avg response time | 5s                | 900ms           |
| Thread count      | 300               | 80              |
| DB wait time      | High              | Minimal         |
| Throughput        | 1500 rps unstable | 2000 rps stable |

--------


# Key Learnings

## 1. More threads ≠ better performance

Too many threads → context switching overhead.

## 2. Align:

-   Web threads
    
-   Async executor
    
-   DB connection pool
    

## 3. Measure before tuning

Used:

-   `top`
    
-   `jstack`
    
-   Spring Actuator metrics
    
-   Hikari metrics
    
-   Prometheus + Grafana

-----------


# Advanced Optimization (Next Level)

## Use WebClient (Non-blocking)

Switch from:

RestTemplate

To:

WebClient (Reactor Netty)

Now:

-   Event-loop model
    
-   16 threads instead of 200
    
-   Massive scalability improvement

----


# If System is Mostly IO

Best design:

-   Use WebFlux
    
-   Small event loop thread pool
    
-   Non-blocking DB (R2DBC)
    
-   Reactive end-to-end

-----------


Thread pool tuning is about balancing:

CPU  
IO wait time  
DB pool size  
Queue capacity  
Context switching  
Memory

Not about increasing numbers blindly.

-------------

