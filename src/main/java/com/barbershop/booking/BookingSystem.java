package com.barbershop.booking;

import java.time.LocalTime;
import java.time.Month;

public interface BookingSystem {
    // Updated createBooking method to include email and return booking reference
    String createBooking(String customerName, String email, Month month, int day, LocalTime time, String barber);

    // Updated cancelBooking method to use only booking reference
    boolean cancelBooking(String reference);
}
