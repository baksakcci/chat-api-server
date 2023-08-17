package com.example.springstompprogramming.room.domain.repository;

import com.example.springstompprogramming.room.domain.entity.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {
    private Map<String, Room> rooms = new HashMap<>();

    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    public Room findById(String roomId) {
        return rooms.get(roomId);
    }

    public Room saveRoom(String randomId, Room room) {
        rooms.put(randomId, room);
        return room;
    }
}
