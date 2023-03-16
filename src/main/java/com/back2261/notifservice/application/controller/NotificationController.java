package com.back2261.notifservice.application.controller;

import com.back2261.notifservice.domain.service.NotificationService;
import com.back2261.notifservice.interfaces.request.SendNotificationTokenRequest;
import com.back2261.notifservice.interfaces.request.SendNotificationTopicRequest;
import com.back2261.notifservice.interfaces.response.DefaultMessageResponse;
import com.back2261.notifservice.interfaces.response.GetNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notif")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/token")
    public ResponseEntity<DefaultMessageResponse> sendToToken(@RequestBody SendNotificationTokenRequest tokenRequest) {
        return new ResponseEntity<>(notificationService.sendToToken(tokenRequest), HttpStatus.OK);
    }

    @PostMapping("/topic")
    public ResponseEntity<DefaultMessageResponse> sendToTopic(@RequestBody SendNotificationTopicRequest topicRequest) {
        return new ResponseEntity<>(notificationService.sendToTopic(topicRequest), HttpStatus.OK);
    }

    @GetMapping("/showall/{userId}")
    public ResponseEntity<GetNotificationsResponse> showAll(@PathVariable String userId) {
        return new ResponseEntity<>(notificationService.showAll(userId), HttpStatus.OK);
    }
}
