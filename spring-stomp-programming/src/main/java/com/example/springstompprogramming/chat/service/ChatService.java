package com.example.springstompprogramming.chat.service;

import com.example.springstompprogramming.chat.domain.entity.Message;
import com.example.springstompprogramming.chat.domain.entity.MessageType;
import com.example.springstompprogramming.chat.domain.infra.MessageRepository;
import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.room.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Transactional
    public void publishToClient(MessageDto messageDto) {
        // 존재하는 방인지 확인
        roomRepository.findByRoomId(messageDto.getRoomId()).orElseThrow();

        // DB 저장
        Message message = Message.create(messageDto.getMessage(),
            messageDto.getMessageType());
        messageRepository.save(message);

        // 구독한 client 들에게 websocket response
        if (MessageType.ENTER.equals(messageDto.getMessageType())) {
            messageDto.setMessage(messageDto.getSender() + "님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageDto.getRoomId(), messageDto);
    }
}
