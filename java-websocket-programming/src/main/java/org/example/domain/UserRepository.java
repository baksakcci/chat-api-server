package org.example.domain;

import java.util.HashMap;

public class UserRepository {
    private HashMap<String, String> users;

    public UserRepository() {
        users = new HashMap<>();
    }

    public void register(String id, String username) {
        users.put(id, username);
    }

    public String findById(String id) {
        return users.get(id);
    }
}
