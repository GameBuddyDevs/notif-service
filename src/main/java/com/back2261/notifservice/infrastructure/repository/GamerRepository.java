package com.back2261.notifservice.infrastructure.repository;

import com.back2261.notifservice.infrastructure.entity.Gamer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, String> {
    Gamer findByFcmToken(String fcmToken);

    Optional<Gamer> findByEmail(String email);
}
