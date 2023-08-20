package com.example.springstompprogramming.chat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageDto {
    private String roomId;
    private String sender;
    private String message;

    private MessageDto(String roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }

    public static MessageDto toDto(String roomId, String sender, String message) {
        return new MessageDto(roomId, sender, message);
    }
}
