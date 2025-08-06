package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.dto.NotificationDto;
import com.pmq.vnnewsvoice.mapper.NotificationMapper;
import com.pmq.vnnewsvoice.pojo.CustomUserDetails;
import com.pmq.vnnewsvoice.pojo.Notification;
import com.pmq.vnnewsvoice.service.NotificationService;
import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    
    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping("/")
    public String getNotifications(
            Model model, 
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Long userId = principal.getUserInfo().getId();
        List<Notification> notifications = notificationService.getNotificationsByUserIdPaginated(userId, page, size);
        List<NotificationDto> notificationDtos = notifications.stream()
                .map(notification -> notificationMapper.toDto(notification))
                .collect(Collectors.toList());
        
        long totalNotifications = notificationService.countNotificationsByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalNotifications / size);
        
        model.addAttribute("notifications", notificationDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalNotifications);
        model.addAttribute("pageSize", size);
        
        return "dashboard/notifications";
    }

    @PostMapping("/{id}/read")
    @ResponseBody
    public ResponseEntity<?> markAsRead(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        Optional<Notification> notificationOpt = Optional.ofNullable(notificationService.getNotificationById(id));
        
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            
            // Kiểm tra xem thông báo có thuộc về người dùng hiện tại không
            if (notification.getUserId().getId().equals(principal.getUserInfo().getId())) {
                notification.setIsRead(true);
                Notification updatedNotification = notificationService.updateNotification(notification);
                NotificationDto notificationDto = notificationMapper.toDto(updatedNotification);
                return ResponseEntity.ok(notificationDto);
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/mark-all-read")
    @ResponseBody
    public ResponseEntity<?> markAllAsRead(@AuthenticationPrincipal CustomUserDetails principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Long userId = principal.getUserInfo().getId();
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        
        for (Notification notification : notifications) {
            if (!notification.getIsRead()) {
                notification.setIsRead(true);
                notificationService.updateNotification(notification);
            }
        }
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Long> getUnreadCount(@AuthenticationPrincipal CustomUserDetails principal) {
        if (principal == null) {
            return ResponseEntity.ok(0L);
        }
        long count = notificationService.countUnreadNotifications();
        return ResponseEntity.ok(count);
    }
}