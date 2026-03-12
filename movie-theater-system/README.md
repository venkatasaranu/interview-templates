# 🎬 Movie Theater Booking System – Backend Exercise

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
## 🎥 Challenge Overview
You are building a RESTful backend for a **movie theater booking system**.

### The theater has:
* Multiple screens, each with a seating layout (rows and seats per row)
* Seats categorized by type: `STANDARD`, `PREMIUM`, `RECLINER`, `IMAX`
* Movies with showtimes scheduled for specific screens
* Customers can book one or multiple seats for a showtime
___
## 📡 API Specifications

### 1. Add a Movie
**Endpoint**: `POST` `/api/movies`

**Request Body**:
```json
{
  "title": "Inception",
  "genre": "SCI_FI",
  "durationMinutes": 148,
  "rating": "PG_13",
  "language": "English"
}
```
**Response**: `201 Created` with movieId
___
### 2. Create a Showtime
**Endpoint**: `POST` `/api/showtimes`

**Request Body**:
```json
{
  "movieId": "mov-001",
  "screenId": "scr-001",
  "startTime": "2025-07-20T18:00:00",
  "basePrice": 12.00
}
```
**Response**:
* `201 Created` with showtimeId
* `400 Bad Request` if screen is already booked at that time (including movie duration buffer)
___
### 3. Get Available Seats
**Endpoint**: `GET` `/api/showtimes/{showtimeId}/seats`

**Response**:
```json
{
  "showtimeId": "show-001",
  "movieTitle": "Inception",
  "startTime": "2025-07-20T18:00:00",
  "seats": [
    {"seatId": "A1", "row": "A", "number": 1, "type": "STANDARD", "price": 12.00, "available": true},
    {"seatId": "B3", "row": "B", "number": 3, "type": "PREMIUM", "price": 15.60, "available": false}
  ]
}
```
___
### 4. Book Seats
**Endpoint**: `POST` `/api/bookings`

**Request Body**:
```json
{
  "customerId": "cust-001",
  "showtimeId": "show-001",
  "seatIds": ["A1", "A2"]
}
```
**Response**:
* `201 Created` with bookingId, seats booked, total price, and booking reference code
* `400 Bad Request` if any requested seat is already booked
___
### 5. Cancel Booking
**Endpoint**: `DELETE` `/api/bookings/{bookingId}`

**Response**:
* `200 OK` — seats released back to available pool
* `400 Bad Request` if showtime starts in less than 30 minutes
___
### 6. Get Customer Bookings
**Endpoint**: `GET` `/api/customers/{customerId}/bookings`

**Response**: All bookings for the customer with movie, showtime, and seat details
___
### 7. Search Showtimes
**Endpoint**: `GET` `/api/showtimes/search`

**Query Parameters**: `movieId` (optional), `date` (YYYY-MM-DD, optional), `language` (optional)

**Response**: List of matching showtimes with available seat count and price range
___
## 🌐 Bonus Task: Dynamic Seat Pricing
Apply price multipliers based on:
* **Seat type**: STANDARD (1.0x), PREMIUM (1.3x), RECLINER (1.6x), IMAX (2.0x)
* **Time of day**: Matinee before 5pm (0.8x), Evening 5–10pm (1.0x), Late night after 10pm (0.9x)
* **Day type**: Weekday (1.0x), Weekend (1.2x)

> [!NOTE]
> Pricing multipliers are pre-configured in `application.yaml`.
___
## 🧩 Interview Questions to Consider
1. How would you represent the seating layout to efficiently check and update seat availability?
2. How do you prevent two customers from booking the same seat simultaneously?
3. What approach would you use to detect schedule conflicts for a screen?
4. How would you implement a seat suggestion feature for group bookings (consecutive seats together)?
5. How would you design an efficient search for showtimes across hundreds of screens?
