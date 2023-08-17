package com.example.springwebsocketprogramming.domain.repository;

import com.example.springwebsocketprogramming.domain.entity.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class RoomRepository {
    private final Map<String, Room> chatRooms = new HashMap<>(); // roomId, Session

    public List<Room> findAll() {
        return new ArrayList<>(chatRooms.values());
    }

    public Room findById(String roomId) {
        return chatRooms.get(roomId);
    }

    public Room saveRoom(String randomId, Room room) {
        chatRooms.put(randomId, room);
        return room;
    }
}
