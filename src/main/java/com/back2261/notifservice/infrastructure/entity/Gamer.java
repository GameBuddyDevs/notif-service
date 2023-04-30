package com.back2261.notifservice.infrastructure.entity;

import io.github.GameBuddyDevs.backendlibrary.enums.Role;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "gamer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gamer implements UserDetails {
    @Id
    private String userId;

    @Column(name = "username", unique = true)
    private String gamerUsername;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fcmToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
