package com.example.springstompprogramming.chat.domain.infra;

import com.example.springstompprogramming.chat.domain.entity.Session;
import com.example.springstompprogramming.room.domain.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findSessionsByRoom(Room room);
}
