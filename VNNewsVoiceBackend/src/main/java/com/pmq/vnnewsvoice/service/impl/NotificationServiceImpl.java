package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public Notification addNotification(Notification notification) {
        return null;
    }

    @Override
    public Notification getNotificationById(Long id) {
        return null;
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return null;
    }

    @Override
    public boolean deleteNotification(Long id) {
        return false;
    }

    @Override
    public long countNotifications() {
        return 0;
    }

    @Override
    public long countUnreadNotifications() {
        return 0;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return List.of();
    }
}
