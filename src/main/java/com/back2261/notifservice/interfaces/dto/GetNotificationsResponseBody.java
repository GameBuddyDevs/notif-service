package com.back2261.notifservice.interfaces.dto;

import com.back2261.notifservice.base.BaseModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationsResponseBody extends BaseModel {
    List<NotificationDto> userNotifications;
}
