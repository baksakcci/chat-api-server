package org.example;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.example.config.MessageDecoder;
import org.example.config.MessageEncoder;
import org.example.domain.ChattingSessionRepository;
import org.example.domain.Message;
import org.example.domain.UserRepository;

@ServerEndpoint(
    value = "/chat/{username}",
    decoders = MessageDecoder.class,
    encoders = MessageEncoder.class)
public class ChatEndpoint {
    private final ChattingSessionRepository chattingSessionRepository = new ChattingSessionRepository();
    private final UserRepository userRepository = new UserRepository();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        chattingSessionRepository.add(session);

        userRepository.register(session.getId(), username);

        chattingSessionRepository.broadcast(new Message(username, null, "연결되었습니다."));
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        String from = userRepository.findById(session.getId());
        message.setFrom(from);

        chattingSessionRepository.broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        chattingSessionRepository.remove(session);

        String from = userRepository.findById(session.getId());
        chattingSessionRepository.broadcast(new Message(from, null, "연결이 해제되었습니다."));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }
}
