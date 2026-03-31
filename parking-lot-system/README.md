# 🅿️ Parking Lot Management System – Backend Exercise

## 👋 Welcome!

Thank you for taking the time to complete this technical exercise!

The goal of this assessment is to evaluate your:

✅ Object-Oriented Programming (OOP) skills

✅ Understanding of RESTful API design

✅ Ability to structure clean, maintainable backend code using Java and Spring Boot

This is your opportunity to showcase how you approach problems, design systems, and apply best practices. Feel free to use any libraries you're comfortable with. You can include comments or documentation to explain your decisions.

---

## 🧠 What We're Looking For
* Clean and maintainable OOP design
* Proper use of RESTful conventions
* Separation of concerns and modular architecture
* Code readability and clarity

There are no trick questions — just a chance to demonstrate how you think and build.

---

## ⚙️ Technical Expectations
The provided project uses **Java 21** and **Spring Boot 3.x**

Please adhere to the following technical guidelines:

🗃️ Use in-memory storage (e.g., Map, List) — no need for a database

📦 Use Java Records for DTOs if desired

🔄 Include DTO-to-Entity mapping where applicable

🛡️ Implement input validation and exception handling

---

## 🏗️ Challenge Overview

You are building a RESTful backend for a **multi-floor parking lot management system**.

### The parking lot has:
* Multiple floors, each with multiple parking spots
* Spots of different types: **COMPACT**, **LARGE**, **HANDICAPPED**
* Vehicles of different types: **MOTORCYCLE**, **CAR**, **TRUCK**
* Vehicles are issued a ticket on entry and pay a fee on exit

### Fee Rules:
| Vehicle Type | Rate |
|---|---|
| MOTORCYCLE | $1.00 / hour |
| CAR | $2.00 / hour |
| TRUCK | $4.00 / hour |

---

## 📡 API Specifications

### 1. Park a Vehicle
**Endpoint**: `POST` `/api/parking/entry`

**Description**: A vehicle enters the lot. Assign the first available spot and issue a ticket.

**Request Body**:
```json
{
  "licensePlate": "ABC-1234",
  "vehicleType": "CAR"
}
```

**Response**:
* `200 OK` with ticket details if a spot is available
```json
{
  "ticketId": "t-001",
  "spotId": "F1-S04",
  "floorNumber": 1,
  "entryTime": "2025-07-20T09:00:00"
}
```
* `400 Bad Request` if the parking lot is full

---

### 2. Exit Parking Lot (Pay & Leave)
**Endpoint**: `POST` `/api/parking/exit/{ticketId}`

**Description**: Calculate the fee based on duration and vehicle type, free up the spot, and close the ticket.

**Response**:
* `200 OK` with exit summary
```json
{
  "ticketId": "t-001",
  "licensePlate": "ABC-1234",
  "entryTime": "2025-07-20T09:00:00",
  "exitTime": "2025-07-20T11:30:00",
  "totalFee": 5.00
}
```
* `404 Not Found` if ticket does not exist
* `400 Bad Request` if ticket is already paid

---

### 3. Get Availability
**Endpoint**: `GET` `/api/parking/availability`

**Description**: Return the total and available spot count, broken down by spot type.

**Response**:
```json
{
  "totalSpots": 90,
  "availableSpots": 47,
  "availableByType": {
    "COMPACT": 20,
    "LARGE": 15,
    "HANDICAPPED": 12
  }
}
```

---

### 4. Get Ticket Details
**Endpoint**: `GET` `/api/tickets/{ticketId}`

**Description**: Retrieve details of a specific ticket.

**Response**:
* `200 OK` with ticket object
* `404 Not Found` if ticket does not exist

---

## 🌟 Bonus Task: Smart Spot Assignment

Implement a smart spot assignment strategy:
* **MOTORCYCLE** → prefers COMPACT spots, falls back to LARGE
* **CAR** → prefers COMPACT spots, falls back to LARGE
* **TRUCK** → requires LARGE spots only
* **HANDICAPPED** spots are reserved for vehicles displaying a handicap permit (add a `boolean handicapPermit` field to the request)

This task evaluates your ability to apply the **Strategy Pattern** and handle edge cases cleanly.
