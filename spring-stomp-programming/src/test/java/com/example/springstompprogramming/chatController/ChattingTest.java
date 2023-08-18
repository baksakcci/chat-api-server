package com.example.springstompprogramming.chatController;

import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.chat.dto.MessageType;
import com.example.springstompprogramming.room.domain.entity.Room;
import io.restassured.RestAssured;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChattingTest {
    @LocalServerPort
    private int port;

    StompSession session;

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException, TimeoutException {
        // init
        RestAssured.port = port;

        // setting
        WebSocketStompClient webSocketStompClient = webSocketStompClient();
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // connection
        CompletableFuture<StompSession> connectAsync = webSocketStompClient.connectAsync(
            "ws://localhost:" + port + "/websocket-stomp",
            new StompSessionHandlerAdapter() {
            });
        session = connectAsync.get(60, TimeUnit.SECONDS);
    }

    @DisplayName("채팅방을 만들고 유저가 해당 채팅방을 입장한다.")
    @Test
    void enterRoomAndBroadCastMessage() throws InterruptedException {
        // init - given
        BlockingQueue<MessageDto> queue = new LinkedBlockingQueue<>();
        Room room = Room.create("testRoom1");
        MessageDto testMessage = MessageDto.toDto(MessageType.ENTER, room.getRoomId(), "baksakcci",
            "");

        // subscribe & send - when
        session.subscribe(String.format("/sub/chat/rooms/%s", room.getRoomId()),
            new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return MessageDto.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    queue.add((MessageDto) payload);
                }
            });
        session.send("/pub/chat/message", testMessage);

        MessageDto poll = queue.poll(60, TimeUnit.SECONDS);
        System.out.println("roomId: " + poll.getRoomId());
        System.out.println("sender: " + poll.getSender());
        Assertions.assertThat(poll.getRoomId()).isEqualTo(room.getRoomId());
        Assertions.assertThat(poll.getMessageType()).isEqualTo(MessageType.ENTER);
    }

    private WebSocketStompClient webSocketStompClient() {
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        WebSocketTransport webSocketTransport = new WebSocketTransport(standardWebSocketClient);
        List<Transport> transports = Collections.singletonList(webSocketTransport);
        SockJsClient sockJsClient = new SockJsClient(transports);

        return new WebSocketStompClient(sockJsClient);
    }
}
