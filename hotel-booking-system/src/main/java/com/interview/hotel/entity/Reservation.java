package com.interview.hotel.entity;

import java.time.LocalDate;

public record Reservation(
    String reservationId,
    String guestId,
    String roomId,
    LocalDate checkIn,
    LocalDate checkOut,
    int numberOfGuests,
    String specialRequests,
    ReservationStatus status,
    double totalCost
) {}
