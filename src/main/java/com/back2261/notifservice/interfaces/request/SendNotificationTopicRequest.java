package com.back2261.notifservice.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendNotificationTopicRequest {
    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;

    private String imageUrl;
}
