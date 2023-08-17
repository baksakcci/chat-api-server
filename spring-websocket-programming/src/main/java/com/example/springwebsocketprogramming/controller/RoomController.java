package com.example.springwebsocketprogramming.controller;

import com.example.springwebsocketprogramming.domain.entity.Room;
import com.example.springwebsocketprogramming.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final ChatService chatService;

    @PostMapping("/rooms")
    public Room createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/rooms")
    public List<Room> findAll() {
        return chatService.findAll();
    }
}
