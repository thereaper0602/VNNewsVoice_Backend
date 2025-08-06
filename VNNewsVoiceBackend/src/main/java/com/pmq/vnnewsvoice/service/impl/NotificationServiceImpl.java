package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.repository.NotificationRepository;
import com.pmq.vnnewsvoice.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Override
    public Notification addNotification(Notification notification) {
        return notificationRepository.addNotification(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.getNotificationById(id);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return notificationRepository.updateNotification(notification);
    }

    @Override
    public boolean deleteNotification(Long id) {
        return notificationRepository.deleteNotification(id);
    }

    @Override
    public long countNotifications() {
        return notificationRepository.countNotifications();
    }

    @Override
    public long countUnreadNotifications() {
        return notificationRepository.countUnreadNotifications();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.getNotificationsByUserId(userId);
    }
    
    @Override
    public List<Notification> getNotificationsByUserIdPaginated(Long userId, int page, int size) {
        return notificationRepository.getNotificationsByUserIdPaginated(userId, page, size);
    }
    
    @Override
    public long countNotificationsByUserId(Long userId) {
        return notificationRepository.countNotificationsByUserId(userId);
    }
}
