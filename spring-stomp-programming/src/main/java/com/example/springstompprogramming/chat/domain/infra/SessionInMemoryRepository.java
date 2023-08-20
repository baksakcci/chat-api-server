package com.example.springstompprogramming.chat.domain.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SessionInMemoryRepository {
    // Key: roomId, value: username
    private static HashMap<String, String> roomSession = new HashMap<>();

    public ArrayList<String> getUserAll(String roomId) {
        ArrayList<String> arr = new ArrayList<>();
        for (Map.Entry<String, String> entry : roomSession.entrySet()) {
            if (entry.getKey().equals(roomId)) {
                arr.add(entry.getValue());
            }
        }
        return arr;
    }

    public void addUser(String roomId, String username) {
        roomSession.put(roomId, username);
    }
}
