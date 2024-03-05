package com.mt.mtSocialMedia.repository;

import com.mt.mtSocialMedia.model.Notification;
import com.mt.mtSocialMedia.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Page<Notification> findAllByReceiver(UserEntity receiver, Pageable pageable);

    Integer countByReceiverAndRead(UserEntity receiver,Boolean read);
}
