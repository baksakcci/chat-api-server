package com.example.springstompprogramming.chat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageDto {
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;

    private MessageDto(MessageType messageType, String roomId, String sender, String message) {
        this.messageType = messageType;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }

    public static MessageDto toDto(MessageType messageType, String roomId, String sender, String message) {
        return new MessageDto(messageType, roomId, sender, message);
    }
}
