package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Notification;

import java.util.List;

public interface NotificationService {
    Notification addNotification(Notification notification);
    Notification getNotificationById(Long id);
    Notification updateNotification(Notification notification);
    boolean deleteNotification(Long id);
    long countNotifications();
    long countUnreadNotifications();
    List<Notification> getNotificationsByUserId(Long userId);
    List<Notification> getNotificationsByUserIdPaginated(Long userId, int page, int size);
    long countNotificationsByUserId(Long userId);
}
