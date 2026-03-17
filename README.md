# Peer-to-Peer Parcel Delivery System (Like Dunzo)

## Description
Implement a **Peer-to-Peer Delivery System** that enables delivering a parcel from one customer to another.

---

# Problem Statement

The system should support the following features.

---

## 1. Customer & Driver Onboarding
The system should allow onboarding of:

- New **customers**
- New **drivers**

---

## 2. Item Configuration
- The list of **items that can be delivered is preconfigured** in the system.
- This list is **fixed and cannot be modified during runtime**.

---

## 3. Order Creation & Cancellation
Customers should be able to:

- **Place an order** for parcel delivery
- **Cancel an order**

---

## 4. Driver Assignment Constraint
- A **driver can pick up only one order at a time**.

---

## 5. Auto Assignment of Orders
Orders should be **automatically assigned to drivers** based on availability.

If **no driver is available**:
- The system should still **accept the order**
- The order should be **assigned when a driver becomes free**

> **Note:**  
> The number of **ongoing orders can exceed the number of drivers**.

---

## 6. Order Lifecycle
Once an order is assigned to a driver:

- The driver should be able to **pick up the order**
- The driver should be able to **mark the order as delivered**

---

## 7. Status Tracking
The system should allow querying:

- **Order Status**
- **Driver Status**

---

## 8. Cancellation Rules
- **Canceled orders must not be assigned to drivers**
- If an **assigned order gets canceled**:
  - The driver **should not be able to pick it up**
  - The driver should become **available for other orders**

---

## 9. Pickup Lock Rule
Once a **driver picks up an order**:

- The order **cannot be canceled**
- This applies to both:
  - Customer
  - System

---

## 10. Assumptions
- Drivers are available **24×7**
- **Travel time is ignored**

---

## 11. Concurrency
- The system should be **thread-safe**
- Handle **all concurrency scenarios properly**

---

# Bonus Features

## Notifications
Notify **customers and drivers** for order updates via:

- Email
- SMS

For this exercise:

- Implement a **vendor class** that simulates email/SMS service
- It should **print logs indicating that the vendor processed the request**

Example:
EMAIL SENT: Order 123 assigned to Driver D1
SMS SENT: Your order has been delivered


---

## Driver Rating
Customers should be able to **rate the driver after delivery**.

---

## Driver Dashboard
Create a dashboard to show **top drivers** based on different strategies:

- Number of completed orders
- Driver rating

---

## Auto Cancel Rule
If **no driver picks up the order within 30 minutes of creation**, the order should be **automatically canceled**.

Important:

- This rule applies **regardless of whether the order is assigned to a driver or not**.

---

# Guidelines

## Time Limit
**120 minutes**

---

## Code Requirements
Write **modular, clean, and demo-able code**.

Provide either:

- **Test cases**, or
- **Runtime execution through a main driver program**

---

## Main Driver
A **main class / driver program** must exist that allows evaluators to test the system with **multiple test cases**.

---

## Design Expectations
Use **design patterns wherever applicable**.

Focus on:

- Proper **entity modeling**
- **Modularity**
- **Extensibility**
- **Separation of concerns**
- **Abstractions**
- **Exception handling**
- **Code readability**
- **Code comments**

---

## Storage
- **Do NOT use external databases** (MySQL, PostgreSQL, etc.)
- Use **in-memory data structures only**

---

## Interfaces
- No need to implement:
  - UI
  - HTTP APIs

The application should be a **standalone program**.

---

# Sample Test Cases

The input/output format does **not need to be exactly the same**.  
These examples illustrate the expected functionality.

## Example Commands


onboard customer - id, name
onboard driver - id, name
create order - customer_id, item_id
cancel order - order_id
show order status - order_id
show driver status - driver_id


## Example Output


Customer C1 onboarded
Driver D1 onboarded

Order O1 created
Order O1 assigned to Driver D1

Driver D1 picked up Order O1
Order O1 delivered

Driver D1 rating: 4.8


---

# Evaluation Criteria

- Demoable & functionally correct code
- Code readability
- Proper entity modeling
- Modularity & extensibility
- Separation of concerns
- Abstractions
- Exception handling
- Code comments