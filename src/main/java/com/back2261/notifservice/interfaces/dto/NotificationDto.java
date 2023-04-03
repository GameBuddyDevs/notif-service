package com.back2261.notifservice.interfaces.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String title;
    private String body;
    private String userIdOrTopic;
    private Date createdDate;
}
