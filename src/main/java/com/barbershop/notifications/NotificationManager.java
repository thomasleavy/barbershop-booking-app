package com.barbershop.notifications;

public class NotificationManager implements NotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("Notification: " + message);
    }
}
