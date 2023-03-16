package com.back2261.notifservice.interfaces.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {
    private String title;
    private String body;
    private String userId;
    private Date createdDate;
}
