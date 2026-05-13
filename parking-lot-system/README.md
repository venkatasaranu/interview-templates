# 🅿️ Parking Lot Management System – Backend Exercise

## 👋 Welcome!

Thank you for taking the time to complete this technical exercise!

The goal of this assessment is to evaluate your:

✅ Object-Oriented Programming (OOP) skills

✅ Understanding of RESTful API design

✅ Ability to structure clean, maintainable backend code using Java and Spring Boot

This is your opportunity to showcase how you approach problems, design systems, and apply best practices. Feel free to use any libraries you're comfortable with. You can include comments or documentation to explain your decisions.
___
## 🧠 What We're Looking For
* Clean and maintainable OOP design
* Proper use of RESTful conventions
* Separation of concerns and modular architecture
* Code readability and clarity

There are no trick questions — just a chance to demonstrate how you think and build.
___
## ⚙️ Technical Expectations
The provided project uses **Java 21** and **Spring Boot 3.x**

Please adhere to the following technical guidelines:

🗃️ Use in-memory storage (e.g., Map, List) — no need for a database

📦 Use Java Records for DTOs if desired

🔄 Include DTO-to-Entity mapping where applicable

🛡️ Implement input validation and exception handling
___
## 🏗️ Challenge Overview
You are building a RESTful backend for a **multi-floor parking lot management system**.

### The parking lot has:
* Multiple floors, each with a fixed number of spots
* Spots categorized by type: `COMPACT`, `REGULAR`, `LARGE`, `MOTORCYCLE`
* Vehicles of different types that must park in compatible spots
* A ticketing system to track entry/exit and calculate fees
___
## 📡 API Specifications

### 1. Initialize Parking Lot
**Endpoint**: `POST` `/api/parking-lot`

**Description**: Configure the parking lot with floors and spots.

**Request Body**:
```json
{
  "name": "Downtown Parking",
  "floors": [
    {
      "floorNumber": 1,
      "spots": {
        "COMPACT": 20,
        "REGULAR": 30,
        "LARGE": 10,
        "MOTORCYCLE": 15
      }
    }
  ]
}
```
**Response**: `201 Created` with parking lot ID
___
### 2. Park a Vehicle
**Endpoint**: `POST` `/api/parking-lot/{lotId}/park`

**Description**: Park a vehicle and issue a parking ticket.

**Request Body**:
```json
{
  "licensePlate": "ABC-1234",
  "vehicleType": "COMPACT"
}
```
**Response**:
* `200 OK` with ticket details (ticketId, spotId, floor, entryTime)
* `400 Bad Request` if no compatible spot available
___
### 3. Unpark a Vehicle
**Endpoint**: `POST` `/api/parking-lot/{lotId}/unpark`

**Description**: Release a parking spot and calculate the fee.

**Request Body**:
```json
{
  "ticketId": "TKT-001"
}
```
**Response**:
* `200 OK` with fee amount and duration parked
* `404 Not Found` if ticket does not exist
___
### 4. Get Availability
**Endpoint**: `GET` `/api/parking-lot/{lotId}/availability`

**Description**: Get available spots grouped by floor and type.

**Response Body**:
```json
{
  "lotId": "lot-001",
  "floors": [
    {
      "floorNumber": 1,
      "available": {
        "COMPACT": 18,
        "REGULAR": 30,
        "LARGE": 9,
        "MOTORCYCLE": 15
      }
    }
  ]
}
```
___
### 5. Get Ticket Details
**Endpoint**: `GET` `/api/parking-lot/tickets/{ticketId}`

**Response Body**:
```json
{
  "ticketId": "TKT-001",
  "licensePlate": "ABC-1234",
  "spotId": "F1-R-05",
  "floor": 1,
  "spotType": "REGULAR",
  "entryTime": "2025-07-20T09:00:00",
  "exitTime": null,
  "status": "ACTIVE",
  "fee": null
}
```
___
## 🌐 Bonus Task: Dynamic Pricing
Implement a dynamic pricing model where fees vary based on:
* **Vehicle type**: Motorcycles pay less than cars
* **Duration**: First 2 hours at base rate, subsequent hours at 1.5x rate
* **Peak hours**: Weekday 8–10am and 5–7pm, apply a 1.25x multiplier
* **Day type**: Weekend flat rate (different from weekday)

**Requirements:**
* Fee calculation logic should be in a dedicated `PricingService`
* Pricing configuration (base rates, multipliers) should be in `application.yaml`
* Return a detailed fee breakdown in the unpark response

> [!NOTE]
> Pricing config values are pre-defined in `application.yaml` for your convenience.
___
## 🧩 Interview Questions to Consider
1. How would you ensure thread-safety when multiple vehicles try to park simultaneously?
2. What data structure best represents parking spot state and why?
3. How would you extend the system to support reserved/VIP spots?
4. How would you design a license plate recognition integration?
5. How would you handle a vehicle that parks but the ticket is lost?
