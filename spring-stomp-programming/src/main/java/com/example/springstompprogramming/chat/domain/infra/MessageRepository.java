package com.example.springstompprogramming.chat.domain.infra;

import com.example.springstompprogramming.chat.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
