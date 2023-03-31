package com.back2261.notifservice.domain.service;

import com.back2261.notifservice.domain.service.firebase.FCMService;
import com.back2261.notifservice.infrastructure.entity.Notification;
import com.back2261.notifservice.infrastructure.repository.GamerRepository;
import com.back2261.notifservice.infrastructure.repository.NotificationRepository;
import com.back2261.notifservice.interfaces.dto.GetNotificationsResponseBody;
import com.back2261.notifservice.interfaces.dto.NotificationDto;
import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.back2261.notifservice.interfaces.response.GetNotificationsResponse;
import io.github.GameBuddyDevs.backendlibrary.base.BaseBody;
import io.github.GameBuddyDevs.backendlibrary.base.Status;
import io.github.GameBuddyDevs.backendlibrary.enums.TransactionCode;
import io.github.GameBuddyDevs.backendlibrary.exception.BusinessException;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageBody;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

    private final FCMService fcmService;
    private final NotificationRepository notificationRepository;
    private final GamerRepository gamerRepository;

    @Override
    public DefaultMessageResponse sendToToken(SendNotificationTokenRequest tokenRequest) {
        try {
            fcmService.sendMessageToToken(tokenRequest);
            String userId =
                    gamerRepository.findByFcmToken(tokenRequest.getToken()).getUserId();
            saveNotification(tokenRequest.getTitle(), tokenRequest.getBody(), userId);
            DefaultMessageResponse response = new DefaultMessageResponse();
            DefaultMessageBody body = new DefaultMessageBody("Notification sent successfully");
            response.setBody(new BaseBody<>(body));
            response.setStatus(new Status(TransactionCode.DEFAULT_100));
            return response;

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(TransactionCode.NOTIF_SEND_FAILED, e.getMessage());
        }
    }

    @Override
    public DefaultMessageResponse sendToTopic(SendNotificationTopicRequest topicRequest) {
        try {
            fcmService.sendMessageToTopic(topicRequest);
            saveNotification(topicRequest.getTitle(), topicRequest.getBody(), topicRequest.getTopic());
            DefaultMessageResponse response = new DefaultMessageResponse();
            DefaultMessageBody body = new DefaultMessageBody("Notification sent successfully");
            response.setBody(new BaseBody<>(body));
            response.setStatus(new Status(TransactionCode.DEFAULT_100));
            return response;

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(TransactionCode.NOTIF_SEND_FAILED, e.getMessage());
        }
    }

    @Override
    public GetNotificationsResponse showAll(String userId) {
        List<Notification> notificationList = notificationRepository.findAllByUserId(userId);
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        BeanUtils.copyProperties(notificationList, notificationDtoList);
        GetNotificationsResponse response = new GetNotificationsResponse();
        GetNotificationsResponseBody body = new GetNotificationsResponseBody();
        body.setUserNotifications(notificationDtoList);
        response.setBody(new BaseBody<>(body));
        response.setStatus(new Status(TransactionCode.DEFAULT_100));
        return response;
    }

    private void saveNotification(String title, String body, String idOrTopic) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setBody(body);
        notification.setUserId(idOrTopic);
        notificationRepository.save(notification);
    }
}
