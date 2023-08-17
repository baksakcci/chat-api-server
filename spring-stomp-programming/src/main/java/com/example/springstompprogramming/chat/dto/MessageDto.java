package com.example.springstompprogramming.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
