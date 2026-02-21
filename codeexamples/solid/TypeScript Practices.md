
## Mapped Type

```ts
// Base model
interface Todo {
  title: string;
  description: string;
  completed: boolean;
}

// Make all properties optional (GENERIC FIXED)
type OptionalTodo<T> = {
  [K in keyof T]?: T[K];
};

// Add 'readonly' and make required
type RequiredTodo<T> = {
  readonly [K in keyof T]-?: T[K];
};

// Remove 'readonly' and '?' modifiers
type ConcreteTodo<T> = {
  -readonly [K in keyof T]-?: T[K];
};


// 1️. OptionalTodo (PATCH Scenario)
const patch: OptionalTodo<Todo> = {
  completed: true,
};
console.log("OptionalTodo object:", patch);


// 2️. RequiredTodo Example
const clean: RequiredTodo<Todo> = {
  title: "Learn TS",
  description: "Mapped types",
  completed: false,
};
// This will now error because it's readonly
// clean.title = "Updated Title";
console.log("RequiredTodo object:", clean);

// 3. ConcreteTodo Example
const concrete1: ConcreteTodo<Todo> = {

  title: "Learn TS",
  description: "Mapped types",
  completed: false,
}
concrete1.title = "Updated Title";
console.log("ConcreteTodo object:", concrete1);

```

<img width="1364" height="557" alt="image" src="https://github.com/user-attachments/assets/a3927c07-f285-4ef7-b617-1b8211eed7f3" />
<img width="1365" height="600" alt="image" src="https://github.com/user-attachments/assets/d99e77d5-c290-4dd6-9532-c6d3aa727417" />


=============

## Template Lierals

### Example : Event Bus

```ts
type DomainEvent = "userCreated" | "orderPaid";

type EventName = `on${Capitalize<DomainEvent>}`;

class EventBus {
  on(event: EventName, callback: () => void) {
    console.log("Registered:", event);
  }
}

const bus = new EventBus();

bus.on("onUserCreated", () => {}); 
bus.on("onOrderPaid", () => {});  
bus.on("onuserCreated", () => {});  // error
```

<img width="1365" height="546" alt="image" src="https://github.com/user-attachments/assets/ecfef7e4-b1d5-4dfa-b66c-9756e7cf32bf" />
<img width="1364" height="553" alt="image" src="https://github.com/user-attachments/assets/c3d27d35-e1e1-4bbc-9dc1-bcf89b821955" />


> TypeScript errors do NOT stop JavaScript from running.

You are seeing logs for `"onuserCreated"` because:

-   TypeScript shows a compile-time error
    
-   But JavaScript still executes at runtime
    

TypeScript types are completely erased after compilation.


TypeScript shows error:

Argument of type '"onuserCreated"' is not assignable to type ...

BUT…

After compilation, JavaScript becomes:

```js
  bus.on("onuserCreated", () => {});
```

JavaScript does not know about `EventName`.

So it runs.

| Layer      | What It Does        |
| ---------- | ------------------- |
| TypeScript | Compile-time safety |
| JavaScript | Runtime execution   |



### If you want runtime protection, you must validate:

```ts
type  DomainEvent  =  "userCreated"  |  "orderPaid";  
  
const  allowedEvents  = ["onUserCreated", "onOrderPaid"] as  const;  
  
type  EventName  =  typeof  allowedEvents[number];  
  
class  EventBus {  
 on(event: string, callback: () => void) {  
  if (!allowedEvents.includes(event  as  EventName)) {  
  throw  new  Error(`Invalid event: ${event}`);  
 }  
  
  console.log("Registered:", event);  
 }  
}
```

### Now this will throw:

```ts
bus.on("onuserCreated", () => {});
```

### Runtime error:

Invalid event: onuserCreated

================


##  Built-in string manipulation types

```ts
type T1 = Uppercase<'TypeScript'>;  // 'HELLO'
type T2 = Lowercase<'TypeScript'>;  // 'world'
type T3 = Capitalize<'TypeScript'>;  // 'Typescript'
type T4 = Uncapitalize<'TypeScript'>;  // 'typeScript'

// Create an event handler type
type EventType = 'click' | 'change' | 'keydown';
type EventHandler = `on${Capitalize<EventType>}`;


const handler1: EventHandler = "onClick";   
const handler2: EventHandler = "onChange";  
const handler3: EventHandler = "onKeydown"; 

console.log(handler1);
console.log(handler2);
console.log(handler3);

const handler4: EventHandler = "onclick";
console.log(handler4);
```

## output:

<img width="1353" height="598" alt="image" src="https://github.com/user-attachments/assets/805e88bd-db55-4fd0-9eca-85baa842803c" />

<img width="1353" height="550" alt="image" src="https://github.com/user-attachments/assets/ff05e794-d8b1-4ada-8ca0-62508a9abf8a" />

<img width="1356" height="551" alt="image" src="https://github.com/user-attachments/assets/c1b403a2-52d3-4c6a-bb32-08789405fffe" />

==================

# Type Guards

A **type guard** is a runtime check that tells TypeScript:

> “Inside this block, the type is narrowed.”

### 1. typeof 

`typeof` — For Primitives

Use when checking:

-   string
    
-   number
    
-   boolean
    
-   bigint
    
-   symbol
    
-   undefined
    
-   function
  
```ts
function print(value: string | number) {
  if (typeof value === "string") {
    return value.toUpperCase(); // value: string
  } else {
    return value.toFixed(2); // value: number
  }
}

console.log(print("typescript"));
console.log(print(99.99999));
```

typeof "abc"       // "string"
typeof 123         // "number"
typeof true        // "boolean"
typeof undefined   // "undefined"
typeof {}          // "object"
typeof []          // "object"

------------

### 2. instanceof

Checks if object was created using a constructor.

```ts

class User {
  constructor(public name: string) {}
}

function greet(obj: User | Date): string {
  if (obj instanceof User) {
    return obj.name;
  } else {
    return obj.toISOString();
  }
}

const user1 = new User("sankar");

console.log("User :", greet(user1));
console.log("Date :", greet(new Date()));

```

<img width="1353" height="503" alt="image" src="https://github.com/user-attachments/assets/ab3648d1-d197-4f45-9dd4-6f763b01828c" />

### This fails with plain objects

```ts
const fakeUser = { name: "Siva" };

console.log(fakeUser instanceof User); // false
```

Even though structure matches!

Because:

instanceof checks prototype chain, not structure.


# Structural vs Prototype-Based

TypeScript is:

✔ Structural (shape-based)

JavaScript `instanceof` is:

✔ Prototype-based (class-based)



## When to Use What

### Use `typeof` when:

-   Working with primitives
    
-   Handling union types like `string | number`
    
-   Narrowing unknown values
    

----------

### Use `instanceof` when:

-   Working with class instances
    
-   Built-in objects (Date, Error, Map)
    
-   You control object creation

### API Response (Never use instanceof)

```ts
type User = {
  id: string;
  name: string;
};

function isUser(obj: any): obj is User {
  return (
    typeof obj === "object" &&
    obj !== null &&
    typeof obj.id === "string" &&
    typeof obj.name === "string"
  );
}
```

## Advanced Example: Built-in Classes

```ts
function handle(input: Date | Error) {
  if (input instanceof Date) {
    console.log(input.toISOString());
  } else {
    console.log(input.message);
  }
}
```

-------------

| Feature               | `typeof`                      | `instanceof`              |
| --------------------- | ----------------------------- | ------------------------- |
| Checks                | Primitive type                | Class instance            |
| Works on              | string, number, boolean, etc. | Objects created via `new` |
| Runtime?              | Yes                           | Yes                       |
| TS narrowing?         | Yes                           | Yes                       |
| Uses prototype chain? |  No                          | Yes                        |

-------------------

| Use Case                      | Use               |
| ----------------------------- | ----------------- |
| Primitive union               | `typeof`          |
| Class instance                | `instanceof`      |
| API JSON object               | Custom type guard |
| Built-in object (Date, Error) | `instanceof`      |
| Array check                   | `Array.isArray()` |

---------------------


In enterprise systems:

-   `typeof` → primitive narrowing
    
-   `instanceof` → domain objects
    
-   Custom guards → API boundary validation
    
-   Discriminated unions → best pattern for domain modeling

---------------------------

##  Custom guards → API boundary validation

We mean:

- Never trust external data
- Validate at the boundary
- Narrow unknown → Safe domain type
- Prevent runtime crashes

### Step 1 — Define Domain Model

```ts
type User = {
  id: string;
  name: string;
  isActive: boolean;
};
```

### Step 2 — Create Custom Type Guard

## Basic Example

```ts
function isString(value: unknown): value is string {
  return typeof value === "string";
}
```

Usage:

```ts
function process(input: unknown) {
  if (isString(input)) {
    console.log(input.toUpperCase()); // input: string
  }
}
```

Notice the return type:

```ts
value is string
```

That is the type predicate.

-----------------


```ts

function isUser(obj: unknown): obj is User {
  return (
    typeof obj === "object" &&
    obj !== null &&
    "id" in obj &&
    "name" in obj &&
    "isActive" in obj &&
    typeof (obj as any).id === "string" &&
    typeof (obj as any).name === "string" &&
    typeof (obj as any).isActive === "boolean"
  );
}
```

### Usage:

```ts
const response: unknown = JSON.parse('{"id":"1","name":"Siva"}');

if (isUser(response)) {
  console.log("Valid user:", response.name);
} else {
  console.log("Invalid response");
}
```


This:

  - Checks runtime structure
  
  - Narrows compile-time type

### Step 3 — Use at API Boundary

```ts

async function fetchUser(): Promise<User> {
  const response = await fetch("/api/user");
  const data: unknown = await response.json();

  if (!isUser(data)) {
    throw new Error("Invalid user response from API");
  }

  return data; // fully typed User
}
```
Now:

  Runtime safe
  
  Compile-time safe
  
  Domain protected

--------------

### Why This Is Important

Without guard:

```ts
const user = await fetchUser();
console.log(user.name.toUpperCase()); // might crash
```

If backend returns:

```json
{ "id": 1, "name": null }
```

App crashes.

> With guard → fail early.


## Real Enterprise Pattern (Layered Architecture)

```
API Layer
   ↓ (validate)
DTO Layer
   ↓ (map)
Domain Layer
```


### Example:

## Step 1 — API DTO

```ts
type UserDto = {
  id: string;
  name: string;
  isActive: boolean;
};
```

## Step 2 — Guard

```ts
function isUserDto(data: unknown): data is UserDto {
  return (
    typeof data === "object" &&
    data !== null &&
    typeof (data as any).id === "string" &&
    typeof (data as any).name === "string" &&
    typeof (data as any).isActive === "boolean"
  );
}
```

## Step 3 — Map to Domain

```
type DomainUser = {
  id: string;
  displayName: string;
  active: boolean;
};

function mapToDomain(dto: UserDto): DomainUser {
  return {
    id: dto.id,
    displayName: dto.name,
    active: dto.isActive,
  };
}
```

## Step 4 — Safe API Call

```ts
async function getUser(): Promise<DomainUser> {
  const res = await fetch("/api/user");
  const data: unknown = await res.json();

  if (!isUserDto(data)) {
    throw new Error("Invalid API response");
  }

  return mapToDomain(data);
}
```


## Reusable validator:

```ts
function  isObject(value: unknown): value  is  Record<string, unknown> {  
  return  typeof  value  ===  "object"  &&  value  !==  null;  
}
```

Use in other guards to reduce duplication.


# When You MUST Use This

-   External APIs
    
-   Microservices
    
-   Browser localStorage
    
-   Third-party SDKs
    
-   WebSocket messages
    
-   Event bus payloads
    

Never trust external input.

------------------


##
Even Better: Schema Libraries

In large enterprise apps, teams use:

-   Zod
    
-   Yup
    
-   Joi
    

Because writing manual guards is repetitive.

Example concept (simplified):

```ts
const  userSchema  =  z.object({  
 id: z.string(),  
 name: z.string(),  
 isActive: z.boolean(),  
});
```

Then:

```ts
const  user  =  userSchema.parse(data);
```

Runtime + compile-time alignment.

-------------------

At API boundaries:

1.  Accept `unknown`
    
2.  Validate
    
3.  Map to domain
    
4.  Never expose raw DTOs internally
    

This prevents 90% of production crashes.

-----------------

## 4. Discriminated Union (Best Pattern)

This is how enterprise apps structure domain models.

```ts
type Payment =
  | { type: "card"; cardNumber: string }
  | { type: "upi"; upiId: string }
  | { type: "cash" };
```

Type guard via discriminator:

```ts
function processPayment(payment: Payment) {
  switch (payment.type) {
    case "card":
      console.log(payment.cardNumber);
      break;

    case "upi":
      console.log(payment.upiId);
      break;

    case "cash":
      console.log("Cash payment");
      break;
  }
}
```

No custom guard needed — TypeScript auto-narrows.

This is the recommended enterprise pattern.

------------

