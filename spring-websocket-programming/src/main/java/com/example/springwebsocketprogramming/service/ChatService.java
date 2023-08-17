package com.example.springwebsocketprogramming.service;

import com.example.springwebsocketprogramming.controller.dto.MessageDto;
import com.example.springwebsocketprogramming.controller.dto.MessageType;
import com.example.springwebsocketprogramming.domain.entity.Room;
import com.example.springwebsocketprogramming.domain.repository.RoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private final RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findRoom(String roomId) {
        return roomRepository.findById(roomId);
    }

    public Room createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        Room room = Room.create(randomId, name);
        return roomRepository.saveRoom(randomId, room);
    }

    public void handleActions(WebSocketSession session, MessageDto messageDto) {
        Room room = findRoom(messageDto.getRoomId());

        if (messageDto.getMessageType().equals(MessageType.ENTER)) {
            room.getSessions().add(session);
            messageDto.setMessage(messageDto.getSender() + "entered chat room");
        }
        sendMessageToSessions(room, messageDto.getMessage());
    }

    public <T> void sendMessageToSessions(Room room, T message) {
        Set<WebSocketSession> sessions = room.getSessions();
        sessions.parallelStream()
            .forEach(session -> sendMessage(session, message));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
