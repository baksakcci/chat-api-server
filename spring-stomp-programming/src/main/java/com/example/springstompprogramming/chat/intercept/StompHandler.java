package com.example.springstompprogramming.chat.intercept;

import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.chat.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

    /*
    @Autowired
    private ChatService chatService;
    @Autowired
    private ObjectMapper objectMapper;

     */
    @Autowired
    private SimpMessageSendingOperations messageTemplate;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            /*
             연결
             */

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            /*
             구독
             */
            // header로부터 roomId 추출
            String roomId = (String) message.getHeaders().get("simpDestination");
            int roomIdIndex = roomId.lastIndexOf("/");
            if (roomIdIndex != -1) {
                roomId = roomId.substring(roomIdIndex + 1);
            } else {
                roomId = "";
            }

            MessageDto messageDto1 = MessageDto.toDto(roomId, "bak", "bak 님이 입장하였습니다.");
            //messageTemplate.convertAndSend("/sub/chat/rooms/" + roomId, messageDto1);
            /*
            String roomId = chatService.extractRoomId(
                (String) message.getHeaders().get("simpDestination"));
            log.debug("roomId: " + roomId);
            // payload 추출 및 JSON -> Java Object 작업

            MessageDto messageDto = null;
            try {
                messageDto = objectMapper.readValue((String) message.getPayload(), MessageDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.debug("messageDto: " + messageDto);

            //
            // 전송
            chatService.publishToClient(messageDto1);

             */

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            /*
             종료
             */
        }

        return message;
    }
}
