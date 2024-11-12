package com.barbershop.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class BookingManager implements BookingSystem {
    private Map<String, BookingDetails> bookings;
    private List<LocalTime> availableTimes;
    private List<String> barbers;

    // Database URL for SQLite
    private static final String DB_URL = "jdbc:sqlite:newsletter.db";

    public BookingManager() {
        bookings = new HashMap<>();
        availableTimes = generateAvailableSlots();
        barbers = Arrays.asList("Brian", "Thomas", "Eamonn");
        setupDatabase();
    }

    // Interface method implementation
    @Override
    public String createBooking(String customerName, String email, Month month, int day, LocalTime time,
            String barber) {
        // Default to not signing up for the newsletter
        return createBooking(customerName, email, month, day, time, barber, false);
    }

    // Updated to include newsletter sign-up
    public String createBooking(String customerName, String email, Month month, int day, LocalTime time,
            String barber, boolean newsletterSignUp) {
        LocalDateTime slot = createSlotDateTime(month, day, time);

        if (slot != null && !isSlotBooked(slot)) {
            String reference = generateBookingReference();
            bookings.put(reference, new BookingDetails(customerName, email, slot, barber, newsletterSignUp));

            // Add email to the newsletter if the user opted in
            if (newsletterSignUp) {
                addEmailToNewsletter(email);
            }

            showAlert("Booking Successful",
                    "Your booking is confirmed for " + customerName + " on " + formatSlot(slot) +
                            " with " + barber + ".\nReference Number: " + reference
                            + "\nPlease use this to modify or cancel.");
            return reference;
        }

        showAlert("Booking Error", "Slot already booked or invalid date!");
        return null;
    }

    // Cancel a booking using the reference number
    @Override
    public boolean cancelBooking(String reference) {
        if (bookings.containsKey(reference)) {
            bookings.remove(reference);
            showAlert("Booking Canceled", "Your booking with reference " + reference + " has been canceled.");
            return true;
        }
        showAlert("Cancellation Error", "No booking found with reference " + reference);
        return false;
    }

    // Check if a booking exists with the given reference number
    public boolean hasBooking(String reference) {
        return bookings.containsKey(reference);
    }

    // Utility method to generate a unique booking reference
    private String generateBookingReference() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Utility to check if a slot is already booked
    private boolean isSlotBooked(LocalDateTime slot) {
        for (BookingDetails details : bookings.values()) {
            if (details.getSlot().equals(slot)) {
                return true;
            }
        }
        return false;
    }

    private List<LocalTime> generateAvailableSlots() {
        List<LocalTime> times = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            times.add(LocalTime.of(hour, 0));
            times.add(LocalTime.of(hour, 30));
        }
        return times;
    }

    public List<String> getBarbers() {
        return barbers;
    }

    private LocalDateTime createSlotDateTime(Month month, int day, LocalTime time) {
        try {
            LocalDate date = LocalDate.of(2024, month, day);
            return LocalDateTime.of(date, time);
        } catch (Exception e) {
            return null; // Return null if the date is invalid
        }
    }

    private String formatSlot(LocalDateTime slot) {
        return capitalize(slot.getMonth().toString()) + " " + slot.getDayOfMonth() + " at " + slot.toLocalTime();
    }

    private String capitalize(String str) {
        return str.charAt(0) + str.substring(1).toLowerCase();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    // Inner class to hold booking details
    private static class BookingDetails {
        private String customerName;
        private String email;
        private LocalDateTime slot;
        private String barber;
        private boolean newsletterSignUp;

        public BookingDetails(String customerName, String email, LocalDateTime slot, String barber,
                boolean newsletterSignUp) {
            this.customerName = customerName;
            this.email = email;
            this.slot = slot;
            this.barber = barber;
            this.newsletterSignUp = newsletterSignUp;
        }

        public boolean isNewsletterSignUp() {
            return newsletterSignUp;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getSlot() {
            return slot;
        }

        public String getBarber() {
            return barber;
        }
    }

    // Method to set up the database
    private void setupDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {

            // SQL for creating the email table if it does not exist
            String sql = "CREATE TABLE IF NOT EXISTS newsletter_emails ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "email TEXT NOT NULL UNIQUE"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to add email to the newsletter table
    private void addEmailToNewsletter(String email) {
        String sql = "INSERT INTO newsletter_emails(email) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding email to newsletter: " + e.getMessage());
        }
    }

    // Method to print all newsletter emails
    public void printNewsletterEmails() {
        String sql = "SELECT * FROM newsletter_emails";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\tEmail: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
