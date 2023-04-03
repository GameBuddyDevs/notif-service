package com.back2261.notifservice.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import com.back2261.notifservice.domain.service.firebase.FCMService;
import com.back2261.notifservice.infrastructure.entity.Gamer;
import com.back2261.notifservice.infrastructure.entity.Notification;
import com.back2261.notifservice.infrastructure.repository.GamerRepository;
import com.back2261.notifservice.infrastructure.repository.NotificationRepository;
import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.back2261.notifservice.interfaces.response.GetNotificationsResponse;
import io.github.GameBuddyDevs.backendlibrary.exception.BusinessException;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultNotificationServiceTest {

    @InjectMocks
    private DefaultNotificationService defaultNotificationService;

    @Mock
    private FCMService fcmService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private GamerRepository gamerRepository;

    @Test
    void testSendToToken_WhenErrorOccurWhileSendingNotification_ReturnErrorCode114()
            throws ExecutionException, InterruptedException {
        SendNotificationTokenRequest tokenRequest = new SendNotificationTokenRequest();
        tokenRequest.setToken("test");
        tokenRequest.setTitle("test");
        tokenRequest.setBody("test");
        tokenRequest.setImageUrl("test");

        Mockito.doThrow(new ExecutionException(new Exception()))
                .when(fcmService)
                .sendMessageToToken(tokenRequest);

        BusinessException exception =
                assertThrows(BusinessException.class, () -> defaultNotificationService.sendToToken(tokenRequest));
        assertEquals(114, exception.getTransactionCode().getId());
    }

    @Test
    void testSendToToken_WhenValidTokenProvided_ReturnSuccess() throws ExecutionException, InterruptedException {
        SendNotificationTokenRequest tokenRequest = new SendNotificationTokenRequest();
        tokenRequest.setToken("test");
        tokenRequest.setTitle("test");
        tokenRequest.setBody("test");
        tokenRequest.setImageUrl("test");

        Mockito.doNothing().when(fcmService).sendMessageToToken(tokenRequest);
        Mockito.when(gamerRepository.findByFcmToken(Mockito.anyString())).thenReturn(new Gamer());

        DefaultMessageResponse result = defaultNotificationService.sendToToken(tokenRequest);
        assertEquals("100", result.getStatus().getCode());
    }

    @Test
    void testSendToTopic_WhenErrorOccurWhileSendingNotificationToTopic_ReturnErrorCode114()
            throws ExecutionException, InterruptedException {
        SendNotificationTopicRequest topicRequest = new SendNotificationTopicRequest();
        topicRequest.setTopic("test");
        topicRequest.setTitle("test");
        topicRequest.setBody("test");
        topicRequest.setImageUrl("test");

        Mockito.doThrow(new ExecutionException(new Exception()))
                .when(fcmService)
                .sendMessageToTopic(topicRequest);

        BusinessException exception =
                assertThrows(BusinessException.class, () -> defaultNotificationService.sendToTopic(topicRequest));
        assertEquals(114, exception.getTransactionCode().getId());
    }

    @Test
    void testSendToTopic_WhenValidTopicProvided_ReturnSuccess() throws ExecutionException, InterruptedException {
        SendNotificationTopicRequest topicRequest = new SendNotificationTopicRequest();
        topicRequest.setTopic("test");
        topicRequest.setTitle("test");
        topicRequest.setBody("test");
        topicRequest.setImageUrl("test");

        Mockito.doNothing().when(fcmService).sendMessageToTopic(topicRequest);

        DefaultMessageResponse result = defaultNotificationService.sendToTopic(topicRequest);
        assertEquals("100", result.getStatus().getCode());
    }

    @Test
    void testShowAll_WhenValidUserIdProvided_ReturnNotificationList() {
        String userId = "test";
        List<Notification> notificationList = new ArrayList<>();
        Notification notification = new Notification();
        notification.setBody("test");
        notification.setTitle("test");
        notification.setUserId("test");
        notification.setId(UUID.randomUUID());
        notification.setCreatedDate(new Date());
        notificationList.add(notification);

        Mockito.when(notificationRepository.findAllByUserId(Mockito.anyString()))
                .thenReturn(notificationList);

        GetNotificationsResponse result = defaultNotificationService.showAll(userId);
        assertEquals("100", result.getStatus().getCode());
        assertEquals(1, result.getBody().getData().getUserNotifications().size());
    }
}
