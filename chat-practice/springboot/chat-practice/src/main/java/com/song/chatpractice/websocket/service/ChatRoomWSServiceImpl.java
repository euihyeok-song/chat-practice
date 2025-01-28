package com.song.chatpractice.websocket.service;

import com.song.chatpractice.websocket.repository.ChatRoomWSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomWSServiceImpl implements ChatRoomWSService {

    private final ChatRoomWSRepository chatRoomWSRepository;

    @Autowired
    public ChatRoomWSServiceImpl(ChatRoomWSRepository chatRoomWSRepository) {
        this.chatRoomWSRepository = chatRoomWSRepository;
    }
}
