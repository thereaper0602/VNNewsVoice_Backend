package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.NotificationRole;

public interface NotificationRoleRepository {
    NotificationRole addNotificationRole(NotificationRole notificationRole);
    NotificationRole getNotificationRoleById(Long id);
}
