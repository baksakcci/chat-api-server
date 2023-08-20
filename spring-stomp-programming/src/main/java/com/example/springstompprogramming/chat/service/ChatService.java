package com.example.springstompprogramming.chat.service;

import com.example.springstompprogramming.chat.domain.entity.Message;
import com.example.springstompprogramming.chat.domain.entity.Session;
import com.example.springstompprogramming.chat.domain.infra.MessageRepository;
import com.example.springstompprogramming.chat.domain.infra.SessionRepository;
import com.example.springstompprogramming.chat.dto.MessageDto;
import com.example.springstompprogramming.chat.dto.MessageResponseDto;
import com.example.springstompprogramming.room.domain.entity.Room;
import com.example.springstompprogramming.room.domain.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Transactional
    public void enter(MessageDto messageDto) {
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageDto.getRoomId()).orElseThrow();

        messageDto.setMessage(messageDto.getSender() + "님이 입장하셨습니다.");
        addUser(room, messageDto.getSender());

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageDto.getRoomId(),
            messageDto.getSender(),
            messageDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageDto.getRoomId(), messageResponseDto);
    }

    @Transactional
    public void publish(MessageDto messageDto) {
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageDto.getRoomId()).orElseThrow();

        // DB 저장
        Message message = Message.create(messageDto.getMessage());
        messageRepository.save(message);

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageDto.getRoomId(),
            messageDto.getSender(),
            messageDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageDto.getRoomId(), messageResponseDto);
    }

    @Transactional
    public void quit(MessageDto messageDto) {
        // 존재하는 방인지 확인
        Room room = roomRepository.findByRoomId(messageDto.getRoomId()).orElseThrow();

        messageDto.setMessage(messageDto.getSender() + "님이 퇴장하셨습니다.");
        removeUser(room, messageDto.getSender());

        // 채팅방 유저 리스트 가져오기
        ArrayList<String> userList = findListAll(room);

        // 전송
        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(messageDto.getRoomId(),
            messageDto.getSender(),
            messageDto.getMessage(),
            userList
        );
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + messageDto.getRoomId(), messageResponseDto);
    }

    // URL(헤더의 simpDestination 정보)로부터 roomId 추출하기
    public String extractRoomId(String destination) {
        int roomIdIndex = destination.lastIndexOf("/");
        if (roomIdIndex != -1) {
            return destination.substring(roomIdIndex + 1);
        } else {
            return "";
        }
    }

    // 현재 접속자 리스트 가져오기
    public ArrayList<String> findListAll(Room room) {
        List<Session> userList = sessionRepository.findSessionsByRoom(room);
        ArrayList<String> usernameList = new ArrayList<>();
        for (Session s : userList) {
            usernameList.add(s.getUsername());
        }
        return usernameList;
    }

    // 채팅방 접속 인원 추가
    public void addUser(Room room, String username) {
        sessionRepository.save(Session.create(username, room));
    }

    // 채팅방 접속 인원 제거
    public void removeUser(Room room, String username) {
        sessionRepository.delete(Session.create(username, room));
    }
}
