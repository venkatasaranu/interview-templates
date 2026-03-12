# 🏨 Hotel Booking System – Backend Exercise

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
## 🏨 Challenge Overview
You are building a RESTful backend for a **hotel room booking system**.

### The hotel has:
* Rooms categorized by type: `SINGLE`, `DOUBLE`, `SUITE`, `PENTHOUSE`
* Each room has a nightly rate and maximum occupancy
* Guests can make reservations with check-in and check-out dates
* Reservations can be cancelled with partial or full refund policies
* Check-in and check-out operations update room availability
___
## 📡 API Specifications

### 1. Add a Room
**Endpoint**: `POST` `/api/rooms`

**Request Body**:
```json
{
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "floor": 1,
  "nightlyRate": 150.00,
  "maxOccupancy": 2,
  "amenities": ["WiFi", "TV", "Mini-bar"]
}
```
**Response**: `201 Created` with roomId
___
### 2. Search Available Rooms
**Endpoint**: `GET` `/api/rooms/available`

**Query Parameters**: `checkIn` (YYYY-MM-DD), `checkOut` (YYYY-MM-DD), `roomType` (optional), `guests` (optional)

**Response**: List of available rooms with nightly rate and total cost for the stay
___
### 3. Make a Reservation
**Endpoint**: `POST` `/api/reservations`

**Request Body**:
```json
{
  "guestId": "g-001",
  "roomId": "r-101",
  "checkIn": "2025-08-01",
  "checkOut": "2025-08-05",
  "numberOfGuests": 2,
  "specialRequests": "High floor preferred"
}
```
**Response**:
* `201 Created` with reservationId and total cost
* `400 Bad Request` if room is unavailable for the date range
___
### 4. Cancel a Reservation
**Endpoint**: `DELETE` `/api/reservations/{reservationId}`

**Response**:
* `200 OK` with refund amount (full refund if cancelled 3+ days before check-in, 50% otherwise)
* `404 Not Found` if reservation not found
___
### 5. Check In
**Endpoint**: `POST` `/api/reservations/{reservationId}/check-in`

**Response**:
* `200 OK` — room status updated to OCCUPIED
* `400 Bad Request` if check-in date is not today or reservation not CONFIRMED
___
### 6. Check Out
**Endpoint**: `POST` `/api/reservations/{reservationId}/check-out`

**Response**:
* `200 OK` with final bill and loyalty points earned
* `400 Bad Request` if reservation is not in CHECKED_IN status
___
### 7. Get Guest Reservation History
**Endpoint**: `GET` `/api/guests/{guestId}/reservations`

**Response**: All reservations for the guest with status and cost details
___
## 🌐 Bonus Task: Loyalty Points System
* Guests earn **10 points per $1** spent
* **GOLD** status at 10,000 points — 5% discount on future bookings
* **PLATINUM** status at 50,000 points — 10% discount + automatic room upgrades when available
* Points are awarded upon checkout
___
## 🧩 Interview Questions to Consider
1. How do you prevent double-booking when two guests try to reserve the same room simultaneously?
2. How would you implement a seasonal pricing strategy?
3. What approach would you use to suggest room upgrades when a better room is available?
4. How would you design the system to support multiple hotel properties?
5. How would you handle overbooking scenarios (common in the hotel industry)?
