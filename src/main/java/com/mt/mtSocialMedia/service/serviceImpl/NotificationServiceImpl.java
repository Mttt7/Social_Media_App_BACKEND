package com.mt.mtSocialMedia.service.serviceImpl;

import com.mt.mtSocialMedia.dto.NotificationResponseDto;
import com.mt.mtSocialMedia.mapper.NotificationMapper;
import com.mt.mtSocialMedia.model.Notification;
import com.mt.mtSocialMedia.model.UserEntity;
import com.mt.mtSocialMedia.repository.NotificationRepository;
import com.mt.mtSocialMedia.repository.UserRepository;
import com.mt.mtSocialMedia.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public Page<NotificationResponseDto> getUserNotificationsPaginate(int pageSize, int pageNumber) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);


        Page<Notification> notificationsPage = notificationRepository.findAllByReceiver(user,
                PageRequest.of(pageNumber,pageSize, Sort.by("createdAt").descending()));

        return new PageImpl<>(notificationsPage.getContent().stream().map(
                NotificationMapper::mapToNotificationResponseDto).collect(Collectors.toList()),
                notificationsPage.getPageable(),
                notificationsPage.getTotalElements());
    }

    @Override
    public NotificationResponseDto markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);

        return NotificationMapper.mapToNotificationResponseDto(notification);
    }

    @Override
    public void createNotification(UserEntity author, UserEntity receiver, String type, Long id) {
        if (Objects.equals(author.getId(), receiver.getId())) return;
        Notification notification = Notification.builder()
                .author(author)
                .receiver(receiver)
                .type(type)
                .contentId(id)
                .read(false)
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public Integer countUnreadNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username).orElse(null);

       return notificationRepository.countByReceiverAndRead(user,false);
    }
}
