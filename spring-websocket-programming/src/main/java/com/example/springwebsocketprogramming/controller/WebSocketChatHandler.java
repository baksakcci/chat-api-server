package com.example.springwebsocketprogramming.controller;

import com.example.springwebsocketprogramming.controller.dto.MessageDto;
import com.example.springwebsocketprogramming.domain.entity.Room;
import com.example.springwebsocketprogramming.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        log.info("payload = {}", payload);

        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);
        chatService.handleActions(session, messageDto);
    }
}
