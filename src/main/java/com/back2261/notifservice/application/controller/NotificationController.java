package com.back2261.notifservice.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notif")
@RequiredArgsConstructor
public class NotificationController {

    @GetMapping("/test")
    public String getNotif() {
        return "Hello World";
    }
}
