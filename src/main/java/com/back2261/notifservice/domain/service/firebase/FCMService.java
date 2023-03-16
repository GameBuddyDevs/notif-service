package com.back2261.notifservice.domain.service.firebase;

import com.back2261.notifservice.interfaces.enums.NotificationParameter;
import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.google.firebase.messaging.*;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    public void sendMessageToToken(SendNotificationTokenRequest tokenRequest)
            throws ExecutionException, InterruptedException {
        Notification notification = Notification.builder()
                .setTitle(tokenRequest.getTitle())
                .setBody(tokenRequest.getBody())
                .setImage(tokenRequest.getImageUrl())
                .build();
        Message message = Message.builder()
                .setToken(tokenRequest.getToken())
                .setNotification(notification)
                .build();
        String response = sendAndGetResponse(message);
        System.out.println("Successfully sent message: " + response);
    }

    public void sendMessageToTopic(SendNotificationTopicRequest topicRequest)
            throws ExecutionException, InterruptedException {
        Notification notification = Notification.builder()
                .setTitle(topicRequest.getTitle())
                .setBody(topicRequest.getBody())
                .setImage(topicRequest.getImageUrl())
                .build();
        String topic = topicRequest.getTopic();
        Message message = Message.builder()
                .setApnsConfig(getApnsConfig(topic))
                .setAndroidConfig(getAndroidConfig(topic))
                .setNotification(notification)
                .setTopic(topic)
                .build();
        String response = sendAndGetResponse(message);
        System.out.println("Successfully sent message: " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue())
                        .setTag(topic)
                        .build())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build())
                .build();
    }
}
