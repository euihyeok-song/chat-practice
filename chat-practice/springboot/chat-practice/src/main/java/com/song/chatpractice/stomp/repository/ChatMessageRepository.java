package com.song.chatpractice.stomp.repository;

import com.song.chatpractice.stomp.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllMsgByRoomId(String roomId);
}
