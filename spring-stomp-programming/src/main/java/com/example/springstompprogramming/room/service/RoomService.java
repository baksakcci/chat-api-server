package com.example.springstompprogramming.room.service;

import com.example.springstompprogramming.room.domain.entity.Room;
import com.example.springstompprogramming.room.domain.repository.RoomRepository;
import com.example.springstompprogramming.room.dto.RoomResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomResponseDto> findAll() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDto> roomResponseDtos = new ArrayList<>();
        for (Room r : rooms) {
            roomResponseDtos.add(RoomResponseDto.toDto(r.getRoomId(), r.getName()));
        }
        return roomResponseDtos;
    }

    public RoomResponseDto findRoom(String roomId) {
        Room room = roomRepository.findById(roomId);
        return RoomResponseDto.toDto(room.getRoomId(), room.getName());
    }

    public RoomResponseDto createRoom(String name) {
        Room room = Room.create(name);
        roomRepository.saveRoom(room.getRoomId(), room);
        return RoomResponseDto.toDto(room.getRoomId(), name);
    }
}
