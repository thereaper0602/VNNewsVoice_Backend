package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.NotificationRole;

public interface NotificationRoleService {
    NotificationRole addNotificationRole(NotificationRole notificationRole);
    NotificationRole getNotificationRoleById(Long id);
}
