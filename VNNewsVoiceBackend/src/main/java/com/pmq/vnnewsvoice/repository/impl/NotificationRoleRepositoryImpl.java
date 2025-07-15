package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.NotificationRole;
import com.pmq.vnnewsvoice.repository.NotificationRoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class NotificationRoleRepositoryImpl implements NotificationRoleRepository {
    @Override
    public NotificationRole addNotificationRole(NotificationRole notificationRole) {
        return null;
    }

    @Override
    public NotificationRole getNotificationRoleById(Long id) {
        return null;
    }
}
