package com.mt.mtSocialMedia.mapper;

import com.mt.mtSocialMedia.dto.NotificationResponseDto;
import com.mt.mtSocialMedia.model.Notification;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    public  static NotificationResponseDto mapToNotificationResponseDto(Notification notification){
        String photoUrl = notification.getAuthor().getPhotoUrl();


        return NotificationResponseDto.builder()
                .id(notification.getId())
                .authorId(notification.getAuthor().getId())
                .authorName(notification.getAuthor().getFirstName())
                .receiverId(notification.getReceiver().getId())
                .type(notification.getType())
                .read(notification.getRead())
                .authorProfilePhotoSrc(photoUrl)
                .contentId(notification.getContentId())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
