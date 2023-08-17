package com.example.springstompprogramming.room.domain.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Room {
    private String name;
    private String roomId;

    private Room(String name, String roomId) {
        this.name = name;
        this.roomId = roomId;
    }

    public static Room create(String name) {
        return new Room(name, UUID.randomUUID().toString());
    }
}
