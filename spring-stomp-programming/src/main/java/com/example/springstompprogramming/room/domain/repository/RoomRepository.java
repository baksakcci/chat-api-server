package com.example.springstompprogramming.room.domain.repository;

import com.example.springstompprogramming.chat.domain.entity.Session;
import com.example.springstompprogramming.room.domain.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomId(String roomId);
    Optional<Room> findByName(String name);

}
