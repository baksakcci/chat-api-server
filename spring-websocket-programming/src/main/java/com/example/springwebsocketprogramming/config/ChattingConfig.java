package com.example.springwebsocketprogramming.config;

import com.example.springwebsocketprogramming.controller.ChattingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class ChattingConfig implements WebSocketConfigurer {

    private final ChattingHandler chattingHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chattingHandler, "ws/chat")
            .setAllowedOrigins("*");
    }
}
