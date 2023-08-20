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

    // 채팅방 입장
    @MessageMapping("chat/enter")
    public void enter(MessageDto message) {
        chatService.enter(message);
    }

    // 메세지 보내기
    @MessageMapping("/chat/message")
    public void message(MessageDto message) {
        chatService.publish(message);
    }

    // 채팅방 퇴장
    @MessageMapping("chat/quit")
    public void quit(MessageDto message) {
        chatService.quit(message);
    }

}