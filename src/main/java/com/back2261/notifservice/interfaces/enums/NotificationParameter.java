package com.back2261.notifservice.interfaces.enums;

public enum NotificationParameter {
    SOUND("default"),
    COLOR("#FF0000");

    private String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
