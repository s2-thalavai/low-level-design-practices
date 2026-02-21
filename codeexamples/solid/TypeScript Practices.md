
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

