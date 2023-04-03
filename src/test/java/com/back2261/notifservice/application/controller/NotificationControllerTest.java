package com.back2261.notifservice.application.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.back2261.notifservice.domain.service.DefaultNotificationService;
import com.back2261.notifservice.interfaces.dto.GetNotificationsResponseBody;
import com.back2261.notifservice.interfaces.dto.NotificationDto;
import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.back2261.notifservice.interfaces.response.GetNotificationsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.GameBuddyDevs.backendlibrary.base.BaseBody;
import io.github.GameBuddyDevs.backendlibrary.base.Status;
import io.github.GameBuddyDevs.backendlibrary.enums.TransactionCode;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageBody;
import io.github.GameBuddyDevs.backendlibrary.interfaces.DefaultMessageResponse;
import io.github.GameBuddyDevs.backendlibrary.service.JwtService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(
        value = NotificationController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultNotificationService defaultNotificationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {}

    @Test
    void testSendToToken_WhenValidRequestProvided_shouldReturnSuccessMessage() throws Exception {
        SendNotificationTokenRequest tokenRequest = new SendNotificationTokenRequest();
        tokenRequest.setImageUrl("https://www.google.com");
        tokenRequest.setToken("token");
        tokenRequest.setTitle("title");
        tokenRequest.setBody("body");

        DefaultMessageResponse messageResponse = new DefaultMessageResponse();
        DefaultMessageBody body = new DefaultMessageBody("test");
        messageResponse.setBody(new BaseBody<>(body));
        messageResponse.setStatus(new Status(TransactionCode.DEFAULT_100));

        Mockito.when(defaultNotificationService.sendToToken(tokenRequest)).thenReturn(messageResponse);

        var request = MockMvcRequestBuilders.post("/notif/token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tokenRequest));
        var response = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    void testSendToTopic_WhenValidRequestProvided_shouldReturnSuccessMessage() throws Exception {
        SendNotificationTopicRequest topicRequest = new SendNotificationTopicRequest();
        topicRequest.setTopic("topic");
        topicRequest.setImageUrl("https://www.google.com");
        topicRequest.setTitle("title");
        topicRequest.setBody("body");

        DefaultMessageResponse messageResponse = new DefaultMessageResponse();
        DefaultMessageBody body = new DefaultMessageBody("test");
        messageResponse.setBody(new BaseBody<>(body));
        messageResponse.setStatus(new Status(TransactionCode.DEFAULT_100));

        Mockito.when(defaultNotificationService.sendToTopic(topicRequest)).thenReturn(messageResponse);

        var request = MockMvcRequestBuilders.post("/notif/topic")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(topicRequest));
        var response = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    void testShowAll_WhenValidUserIdProvided_shouldReturnListOfPreviousNotifications() throws Exception {
        String userId = "test";
        GetNotificationsResponse notificationsResponse = new GetNotificationsResponse();
        GetNotificationsResponseBody responseBody = new GetNotificationsResponseBody();
        List<NotificationDto> notificationList = new ArrayList<>();
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setBody("body");
        notificationDto.setTitle("title");
        notificationDto.setUserIdOrTopic("userId");
        notificationDto.setCreatedDate(new Date());
        notificationList.add(notificationDto);
        notificationList.add(new NotificationDto());
        responseBody.setUserNotifications(notificationList);
        notificationsResponse.setBody(new BaseBody<>(responseBody));

        Mockito.when(defaultNotificationService.showAll(Mockito.anyString())).thenReturn(notificationsResponse);

        var request =
                MockMvcRequestBuilders.get("/notif/showall/{userId}", userId).contentType("application/json");
        var response = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = response.getResponse().getContentAsString();

        GetNotificationsResponse responseObj = objectMapper.readValue(responseJson, GetNotificationsResponse.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals(2, responseObj.getBody().getData().getUserNotifications().size());
    }
}
