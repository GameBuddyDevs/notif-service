package com.back2261.notifservice.interfaces.dto;

import io.github.GameBuddyDevs.backendlibrary.base.BaseModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationsResponseBody extends BaseModel {
    List<NotificationDto> userNotifications;
}
