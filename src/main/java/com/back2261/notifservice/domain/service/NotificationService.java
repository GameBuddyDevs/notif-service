package com.back2261.notifservice.domain.service;

import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.back2261.notifservice.interfaces.response.GetNotificationsResponse;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageResponse;

public interface NotificationService {

    DefaultMessageResponse sendToToken(SendNotificationTokenRequest tokenRequest);

    DefaultMessageResponse sendToTopic(SendNotificationTopicRequest topicRequest);

    GetNotificationsResponse showAll(String userId);
}
