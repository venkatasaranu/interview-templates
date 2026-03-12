package com.interview.hotel.entity;

public record Guest(
    String guestId,
    String name,
    String email,
    String phone,
    GuestTier tier,
    int loyaltyPoints
) {}
