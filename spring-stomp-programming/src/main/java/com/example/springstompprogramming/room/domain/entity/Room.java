package com.example.springstompprogramming.room.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
