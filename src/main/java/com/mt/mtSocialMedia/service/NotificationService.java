package com.mt.mtSocialMedia.service;

import com.mt.mtSocialMedia.dto.NotificationResponseDto;
import com.mt.mtSocialMedia.model.Notification;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {
    Page<NotificationResponseDto> getUserNotificationsPaginate(int pageSize, int pageNumber);

    NotificationResponseDto markNotificationAsRead(Long notificationId);

    void createNotification(UserEntity author, UserEntity receiver, String type, Long id);

    Integer countUnreadNotifications();
}
