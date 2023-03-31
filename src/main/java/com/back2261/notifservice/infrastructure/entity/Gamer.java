package com.back2261.notifservice.infrastructure.entity;

import io.github.GameBuddyDevs.backendlibrary.enums.Role;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "gamer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gamer {
    @Id
    private String userId;

    @Column(name = "username", unique = true)
    private String gamerUsername;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date lastModifiedDate;

    private Boolean isBlocked = false;
    private Boolean isRegistered = false;
    private Boolean isVerified = false;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fcmToken;
}
