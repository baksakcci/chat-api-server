package org.example.domain;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import javax.websocket.Session;

public class ChattingSessionRepository {
    private static Set<Session> chatEndpoints;

    public ChattingSessionRepository() {
        chatEndpoints = new CopyOnWriteArraySet<>();
    }

    public void add(Session session) {
        chatEndpoints.add(session);
    }

    public void remove(Session session) {
        chatEndpoints.remove(session);
    }

    //service logic
    public void broadcast(Message message) {
        for (Session session : chatEndpoints) {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
