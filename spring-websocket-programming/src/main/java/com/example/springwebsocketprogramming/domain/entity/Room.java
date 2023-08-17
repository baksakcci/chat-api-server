package com.example.springwebsocketprogramming.domain.entity;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
public class Room {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    private Room (String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static Room create(String roomId, String name) {
        // 중복 검증 등 검증로직
        return new Room(roomId, name);
    }
}
