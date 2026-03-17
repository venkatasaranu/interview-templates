# 🚗 Ride Sharing System – Backend Exercise

## 👋 Welcome!

Thank you for taking the time to complete this technical exercise!

The goal of this assessment is to evaluate your:

✅ Object-Oriented Programming (OOP) skills

✅ Understanding of RESTful API design

✅ Ability to structure clean, maintainable backend code using Java and Spring Boot
___
## 🧠 What We're Looking For
* Clean and maintainable OOP design
* Proper use of RESTful conventions
* Separation of concerns and modular architecture
* Code readability and clarity
___
## ⚙️ Technical Expectations
The provided project uses **Java 21** and **Spring Boot 3.x**

🗃️ Use in-memory storage (e.g., Map, List) — no need for a database

📦 Use Java Records for DTOs if desired

🔄 Include DTO-to-Entity mapping where applicable

🛡️ Implement input validation and exception handling
___
## 🚕 Challenge Overview
You are building a RESTful backend for a **ride sharing platform**.

### The platform has:
* Drivers with vehicles, location tracking, and availability status
* Riders who can request trips
* Vehicle categories: `ECONOMY`, `COMFORT`, `PREMIUM`, `XL`, `MOTORCYCLE`
* Dynamic fare calculation with surge pricing during peak demand
___
## 📡 API Specifications

### 1. Register Driver
**Endpoint**: `POST` `/api/drivers`

**Request Body**:
```json
{
  "name": "Carlos Rivera",
  "phone": "555-0300",
  "licenseNumber": "DL-12345",
  "vehicle": {
    "licensePlate": "XYZ-789",
    "make": "Toyota",
    "model": "Camry",
    "year": 2022,
    "type": "COMFORT",
    "color": "Silver"
  }
}
```
**Response**: `201 Created` with driverId
___
### 2. Update Driver Status
**Endpoint**: `PATCH` `/api/drivers/{driverId}/status`

**Request Body**:
```json
{
  "status": "ONLINE",
  "latitude": 40.7128,
  "longitude": -74.0060
}
```
**Response**: `200 OK` with updated driver status
___
### 3. Request a Trip
**Endpoint**: `POST` `/api/trips/request`

**Request Body**:
```json
{
  "riderId": "rider-001",
  "pickupAddress": "Times Square, NYC",
  "pickupLat": 40.7580,
  "pickupLon": -73.9855,
  "dropoffAddress": "JFK Airport, NYC",
  "dropoffLat": 40.6413,
  "dropoffLon": -73.7781,
  "vehicleType": "COMFORT"
}
```
**Response**:
* `201 Created` with tripId, assigned driver, estimated fare, surge multiplier, and ETA
* `400 Bad Request` if no drivers available for the requested vehicle type
___
### 4. Get Trip Details
**Endpoint**: `GET` `/api/trips/{tripId}`

**Response**: Full trip details including driver info, route, fare estimate, and status
___
### 5. Start Trip
**Endpoint**: `POST` `/api/trips/{tripId}/start`

**Description**: Driver confirms rider pickup — trip begins.

**Response**: `200 OK` with trip start time
___
### 6. Complete Trip
**Endpoint**: `POST` `/api/trips/{tripId}/complete`

**Description**: Trip ends at destination — final fare calculated.

**Response**: `200 OK` with fare breakdown (base, distance, time, surge, total)
___
### 7. Cancel Trip
**Endpoint**: `POST` `/api/trips/{tripId}/cancel`

**Response**:
* `200 OK` — no penalty if cancelled within 2 minutes of requesting
* `200 OK` with `$3.00` cancellation fee if cancelled after 2 minutes
___
### 8. Rider Trip History
**Endpoint**: `GET` `/api/riders/{riderId}/trips`

**Response**: All trips for the rider with status, fare, and driver details
___
## 🌐 Bonus Task: Surge Pricing
Apply dynamic pricing when demand is high:
* When **more than 80%** of all ONLINE drivers are ON_TRIP, apply a **1.5x surge multiplier**
* Always show the surge multiplier to the rider before they confirm the trip

> [!NOTE]
> Surge threshold and multiplier are configured in `application.yaml`.
___
## 🧩 Interview Questions to Consider
1. How would you find the nearest available driver efficiently at scale?
2. How would you handle the race condition where two riders request the same driver?
3. How would you design the fare calculation to support different pricing models per city?
4. What strategy would you use to handle drivers going OFFLINE in the middle of a trip?
5. How would you implement a driver rating system that is resistant to manipulation?
