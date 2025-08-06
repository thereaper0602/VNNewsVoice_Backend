package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.dto.NotificationDto;
import com.pmq.vnnewsvoice.mapper.NotificationMapper;
import com.pmq.vnnewsvoice.pojo.CustomUserDetails;
import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class TopbarController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationMapper notificationMapper;

    @ModelAttribute("topbarNotifications")
    public List<NotificationDto> getTopbarNotifications(@AuthenticationPrincipal CustomUserDetails principal) {
        if (principal == null) {
            return List.of();
        }
        
        List<Notification> notifications = notificationService.getNotificationsByUserId(principal.getUserInfo().getId());
        return notifications.stream()
                .map(notification -> notificationMapper.toDto(notification))
                .collect(Collectors.toList());
    }

    @ModelAttribute("unreadNotificationsCount")
    public Long getUnreadNotificationsCount() {
        return notificationService.countUnreadNotifications();
    }
}