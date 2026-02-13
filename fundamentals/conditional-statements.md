
Below are **all common for-loop versions** counting down from **10 to 1** in:

-   Java
    
-   JavaScript
    
-   TypeScript
    
-   Python

------------------------


## Java – All For Loop Variants

### 1. Classic for loop

``` java
for (int  i  =  10; i >= 1; i--) {
    System.out.println(i);
}
``` 

----------

### 2. Multiple variable for loop

``` java
for (int  i  =  10, j = 0; i >= 1; i--, j++) {
    System.out.println(i);
}
``` 

----------

### 3. Infinite for + break

``` java
for (int  i  =  10; ; i--) {
  if (i < 1) break;
    System.out.println(i);
}
``` 

----------

### 4. For-each (using array reverse)

``` java
int[] arr = {10,9,8,7,6,5,4,3,2,1};
for (int num : arr) {
    System.out.println(num);
}
``` 

----------

### 5. Stream API (Java 8+)

``` java
java.util.stream.IntStream
    .iterate(10, i -> i - 1)
    .limit(10)
    .forEach(System.out::println);
``` 

----------

## JavaScript – All For Loop Variants

### 1. Classic for

``` js
for (let i = 10; i >= 1; i--) {
  console.log(i);
}
``` 

----------

### 2. Infinite for + break

``` js
let i = 10; for (;;) {
  if (i < 1) break;
    console.log(i);
    i--;
}
``` 

----------

### 3. for...of (array)

``` js
const arr = [10,9,8,7,6,5,4,3,2,1];
for (const num of arr) {
  console.log(num);
}
``` 

----------

### 4. forEach()

``` js
[10,9,8,7,6,5,4,3,2,1].forEach(num => console.log(num));
``` 

----------

### 5. While-based for style

``` js
let x = 10;
while (x >= 1) {
  console.log(x);
    x--;
}
``` 

----------

## TypeScript – All For Loop Variants

(TypeScript is same as JS but typed)

### 1. Classic typed for

``` ts
for (let  i: number = 10; i >= 1; i--) {
  console.log(i);
}
``` 

----------

### 2. for...of

``` ts
const  arr: number[] = [10,9,8,7,6,5,4,3,2,1];
for (const num of arr) {
  console.log(num);
}
``` 

----------

### 3. forEach

``` ts
const  numbers: number[] = [10,9,8,7,6,5,4,3,2,1];
numbers.forEach((num: number) => console.log(num));
``` 

----------

### 4. Infinite loop + break

``` ts
let  i: number = 10;
for (;;) {
  if (i < 1) break;
      console.log(i);
      i--;
}
``` 

----------

## Python – All For Loop Variants

### 1. range()

```py
for i in  range(10, 0, -1):
print(i)
``` 

----------

### 2. Using reversed()

``` py
for i in  reversed(range(1, 11)):
    print(i)
``` 

----------

### 3. Using list

``` py
for i in [10,9,8,7,6,5,4,3,2,1]:
    print(i)
``` 

----------

### 4. While loop

``` py
i = 10  while i >= 1:
    print(i)
    i -= 1
``` 

----------

### 5. Using list comprehension (not recommended for printing)

``` py
[print(i) for i in  range(10, 0, -1)]
```

--------------

## Summary

| Language | Traditional for | Infinite for   | For-each | Functional style |
| -------- | --------------- | -------------- | -------- | ---------------- |
| Java     | ✅               | ✅              | ✅        | ✅ Stream         |
| JS       | ✅               | ✅              | ✅        | ✅ forEach        |
| TS       | ✅               | ✅              | ✅        | ✅ forEach        |
| Python   | ✅ range         | ❌ (no classic) | ✅        | list comp      |


--------------------



# Java – All While Variants

----------

### 1. Standard while loop

```java
int  i  =  10; while (i >= 1) {
    System.out.println(i);
    i--;
}
``` 

----------

### 2. do-while loop (runs at least once)

```java
int  i  =  10; do {
    System.out.println(i);
    i--;
} while (i >= 1);
``` 

----------

### 3. Infinite while + break

```java
int  i  =  10; while (true) { if (i < 1) break;
    System.out.println(i);
    i--;
}
``` 

----------

### 4. While with multiple variables

```java
int  i  =  10, j = 0; while (i >= 1) {
    System.out.println(i);
    i--;
    j++;
}
``` 

----------

### 5. Labeled while (advanced usage)

```java
int  i  =  10;
outer: while (i >= 1) { if (i == 5) break outer;
    System.out.println(i);
    i--;
}
``` 

----------

## JavaScript – All While Variants

----------

### 1. Standard while

```js
let i = 10; while (i >= 1) {
    console.log(i);
    i--;
}
``` 

----------

### 2. do...while

```js
let i = 10; do {
  console.log(i);
    i--;
} while (i >= 1);
``` 

----------

### 3. Infinite loop + break

```js
  let i = 10;
    while (true) {
        if (i < 1) break;
            console.log(i);
    i--;
}
``` 

----------

### 4. While with multiple variables

```js
let i = 10, j = 0;
    while (i >= 1) {
    console.log(i);
    i--;
    j++;
}
``` 

----------

### 5. Using continue

```js
let i = 10;
    while (i >= 1) {
    if (i === 7) {
        i--;
        continue;
    }
    console.log(i);
    i--;
}
``` 

----------

## TypeScript – All While Variants

(TypeScript = JavaScript + types)

----------

### 1. Standard typed while

```ts
let  i: number = 10;
while (i >= 1) {
    console.log(i);
    i--;
}
``` 

----------

### 2. do...while

```ts
let  i: number = 10;
do {
    console.log(i);
    i--;
} while (i >= 1);
``` 

----------

### 3. Infinite + break

```ts
let  i: number = 10;
    while (true) {
        if (i < 1) break;
        console.log(i);
    i--;
}
``` 

----------

### 4. Multiple variables

```ts
let  i: number = 10, j: number = 0;
  while (i >= 1) {
    console.log(i);
    i--;
    j++;
}
``` 

----------

## Python – All While Variants

----------

### 1. Standard while

```py
i = 10  while i >= 1: print(i)
    i -= 1
``` 

----------

### 2. While True + break

```py
i = 10  while  True: if i < 1: break
    print(i)
    i -= 1
``` 

----------

### 3. While with else

```py
i = 10  while i >= 1:
    print(i)
    i -= 1
    else: print("Done")
``` 

(`else` runs when loop exits normally)

----------

### 4. Multiple variables

```py
i = 10 j = 0  while i >= 1: print(i)
    i -= 1 j += 1
``` 

----------

### 5. Using continue

```py
i = 10  while i >= 1: if i == 7:
        i -= 1  continue  print(i)
    i -= 1
``` 

----------

## Comparison Summary

| Language | while | do-while | infinite loop | labeled | else |
| -------- | ----- | -------- | ------------- | ------- | ---- |
| Java     | ✅     | ✅        | ✅             | ✅       | ❌    |
| JS       | ✅     | ✅        | ✅             | ❌       | ❌    |
| TS       | ✅     | ✅        | ✅             | ❌       | ❌    |
| Python   | ✅     | ❌        | ✅             | ❌       | ✅    |

----------
