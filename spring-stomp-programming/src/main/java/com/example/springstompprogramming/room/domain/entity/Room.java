package com.example.springstompprogramming.room.domain.entity;

import com.example.springstompprogramming.chat.domain.entity.Session;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
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

    @OneToMany
    @JoinColumn(name = "session_id")
    private List<Session> userList;

    private Room(String name, String roomId) {
        this.name = name;
        this.roomId = roomId;
    }

    public static Room create(String name) {
        return new Room(name, UUID.randomUUID().toString());
    }
}
