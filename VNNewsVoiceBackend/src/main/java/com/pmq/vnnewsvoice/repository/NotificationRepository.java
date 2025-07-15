package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification addNotification(Notification notification);
    Notification getNotificationById(Long id);
    Notification updateNotification(Notification notification);
    boolean deleteNotification(Long id);
    long countNotifications();
    long countUnreadNotifications();
    List<Notification> getNotificationsByUserId(Long userId);
}
