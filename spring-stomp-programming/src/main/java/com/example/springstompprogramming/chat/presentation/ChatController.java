package com.example.springstompprogramming.chat.presentation;

import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(MessageDto message) {
        chatService.publishToClient(message);
    }

}
