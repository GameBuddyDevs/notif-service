package com.back2261.notifservice.infrastructure.repository;

import com.back2261.notifservice.infrastructure.entity.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByUserId(String userId);
}
