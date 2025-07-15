package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.repository.NotificationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {
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
