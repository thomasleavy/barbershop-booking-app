// Just some emergency code for the UI if it is needed (probably not)

/*
 * package com.barbershop.ui;
 * 
 * import com.barbershop.booking.BookingManager;
 * import com.barbershop.notifications.NotificationManager;
 * 
 * import javax.swing.JOptionPane;
 * import java.time.LocalDateTime;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 * public class ConsoleUI {
 * private BookingManager bookingManager;
 * private NotificationManager notificationManager;
 * 
 * public ConsoleUI() {
 * bookingManager = new BookingManager();
 * notificationManager = new NotificationManager();
 * }
 * 
 * public void start() {
 * while (true) {
 * String[] options = { "Show Available Slots", "Book a Slot",
 * "Cancel a Booking", "Exit" };
 * int choice = JOptionPane.showOptionDialog(null,
 * "Barbershop Booking System",
 * "Choose an option",
 * JOptionPane.DEFAULT_OPTION,
 * JOptionPane.INFORMATION_MESSAGE,
 * null,
 * options,
 * options[0]);
 * switch (choice) {
 * case 0:
 * showAvailableSlots();
 * break;
 * case 1:
 * bookSlot();
 * break;
 * case 2:
 * cancelBooking();
 * break;
 * case 3:
 * JOptionPane.showMessageDialog(null, "Exiting...");
 * return;
 * default:
 * JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
 * }
 * }
 * }
 * 
 * private void showAvailableSlots() {
 * List<String> availableSlots = bookingManager.getAvailableSlots(); //
 * Implement a method to return available
 * // slots as strings
 * if (availableSlots.isEmpty()) {
 * JOptionPane.showMessageDialog(null, "No available slots.");
 * } else {
 * JOptionPane.showMessageDialog(null, String.join("\n", availableSlots));
 * }
 * }
 * 
 * private void bookSlot() {
 * String name = JOptionPane.showInputDialog("Enter your name:");
 * 
 * // Select month
 * String[] months = { "January", "February", "March", "April", "May", "June",
 * "July", "August", "September",
 * "October", "November", "December" };
 * int monthIndex = JOptionPane.showOptionDialog(null,
 * "Choose a month:",
 * "Month Selection",
 * JOptionPane.DEFAULT_OPTION,
 * JOptionPane.QUESTION_MESSAGE,
 * null,
 * months,
 * months[0]);
 * int month = monthIndex + 1; // Month is 1-based in LocalDate
 * 
 * // Select day
 * String dayStr =
 * JOptionPane.showInputDialog("Enter a day of the month (1-31):");
 * int day;
 * try {
 * day = Integer.parseInt(dayStr);
 * if (day < 1 || day > 31)
 * throw new NumberFormatException();
 * } catch (NumberFormatException e) {
 * JOptionPane.showMessageDialog(null,
 * "Invalid day input! Please enter a number between 1 and 31.", "Error",
 * JOptionPane.ERROR_MESSAGE);
 * return;
 * }
 * 
 * // Select time slot
 * String[] timeSlots = { "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
 * "12:00", "12:30", "13:00", "13:30",
 * "14:00", "14:30", "15:00", "15:30", "16:00", "16:30" };
 * String timeSlot = (String) JOptionPane.showInputDialog(null,
 * "Choose a time slot:", "Time Slot Selection",
 * JOptionPane.QUESTION_MESSAGE, null, timeSlots, timeSlots[0]);
 * 
 * // Select barber
 * String[] barbers = { "Brian", "Thomas", "Eamonn" };
 * String barber = (String) JOptionPane.showInputDialog(null,
 * "Choose a barber:", "Barber Selection",
 * JOptionPane.QUESTION_MESSAGE, null, barbers, barbers[0]);
 * 
 * // Create booking
 * if (name != null && barber != null && timeSlot != null) {
 * LocalDateTime slotTime = LocalDateTime.of(2024, month, day,
 * Integer.parseInt(timeSlot.split(":")[0]),
 * Integer.parseInt(timeSlot.split(":")[1]));
 * 
 * if (bookingManager.createBooking(name, slotTime, barber)) {
 * notificationManager
 * .sendNotification("Booking created for " + name + " on " + slotTime +
 * " with " + barber);
 * JOptionPane.showMessageDialog(null, "Booking confirmed!");
 * } else {
 * JOptionPane.showMessageDialog(null,
 * "Slot is already booked or invalid input.");
 * }
 * }
 * }
 * 
 * private void cancelBooking() {
 * String name = JOptionPane.showInputDialog("Enter your name:");
 * String slot =
 * JOptionPane.showInputDialog("Enter a slot to cancel (yyyy-MM-ddTHH:mm):");
 * 
 * if (name != null && slot != null) {
 * LocalDateTime slotTime = LocalDateTime.parse(slot);
 * 
 * if (bookingManager.cancelBooking(name, slotTime)) {
 * notificationManager.sendNotification("Booking canceled for " + name);
 * JOptionPane.showMessageDialog(null, "Booking canceled!");
 * } else {
 * JOptionPane.showMessageDialog(null, "No booking found for this slot.");
 * }
 * }
 * }
 * }
 * 
 */