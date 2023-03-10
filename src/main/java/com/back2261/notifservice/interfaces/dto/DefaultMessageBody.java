package com.back2261.notifservice.interfaces.dto;

import com.back2261.notifservice.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultMessageBody extends BaseModel {
    private String message;
}
