package com.example.springwebsocketprogramming.controller.dto;

import java.util.ArrayList;

public class RoomResponseDto {
    private String roomId;
    private String name;
    private ArrayList<String> sessions;

    private RoomResponseDto(String roomId, String name, ArrayList<String> sessions) {
        this.roomId = roomId;
        this.name = name;
        this.sessions = sessions;
    }

    public static RoomResponseDto toDto(String roomId, String name, ArrayList<String> sessions) {
        return new RoomResponseDto(roomId, name, sessions);
    }
}
