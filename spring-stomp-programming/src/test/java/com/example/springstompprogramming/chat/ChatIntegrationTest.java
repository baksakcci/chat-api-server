package com.example.springstompprogramming.chat;

import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.room.domain.entity.Room;
import com.example.springstompprogramming.room.domain.repository.RoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class ChatIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    StompSession session;
    @Autowired
    RoomRepository roomRepository;

    @BeforeEach
    void setUp()
        throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        /*
         store room to DB
         */
        Room room = Room.create("testRoom1");
        roomRepository.save(room);
        /*
         connection client using stomp
         */
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
        Room room = roomRepository.findByName("testRoom1").orElseThrow(NoSuchElementException::new);
        MessageDto testMessage = MessageDto.toDto(room.getRoomId(), "baksakcci",
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
    }

    private WebSocketStompClient webSocketStompClient() {
        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        WebSocketTransport webSocketTransport = new WebSocketTransport(standardWebSocketClient);
        List<Transport> transports = Collections.singletonList(webSocketTransport);
        SockJsClient sockJsClient = new SockJsClient(transports);

        return new WebSocketStompClient(sockJsClient);
    }
}
