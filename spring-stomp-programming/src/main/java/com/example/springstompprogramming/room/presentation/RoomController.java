package com.example.springstompprogramming.room.presentation;

import com.example.springstompprogramming.room.dto.RoomResponseDto;
import com.example.springstompprogramming.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<RoomResponseDto> createRoom(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(roomService.createRoom(name));
    }
    @GetMapping("/rooms")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponseDto> roomInfo(@PathVariable String roomId) {
        return ResponseEntity.ok(roomService.findRoom(roomId));
    }

}
