package com.pmq.vnnewsvoice.mapper;

import org.springframework.stereotype.Component;

import com.pmq.vnnewsvoice.dto.NotificationDto;
import com.pmq.vnnewsvoice.pojo.Notification;

@Component
public class NotificationMapper {
    public NotificationDto toDto(Notification notification){
        if(notification == null){
            return null;
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notification.getId());
        notificationDto.setUserId(notification.getUserId() != null ? notification.getUserId().getId() : null);
        notificationDto.setTitle(notification.getTitle());
        notificationDto.setContent(notification.getContent());
        notificationDto.setIsRead(notification.getIsRead());
        notificationDto.setCreatedAt(notification.getCreatedAt());
        return notificationDto;
    }
}
