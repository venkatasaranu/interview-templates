package com.interview.hotel.entity;

import java.util.List;

public record Room(
    String roomId,
    String roomNumber,
    RoomType roomType,
    int floor,
    double nightlyRate,
    int maxOccupancy,
    List<String> amenities,
    RoomStatus status
) {}
