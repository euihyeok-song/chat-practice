package com.song.chatpractice.websocket.service;

import com.song.chatpractice.websocket.repository.ChatMessageWSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageWSServiceImpl implements ChatMessageWSService {

    private final ChatMessageWSRepository chatMessageWSRepository;

    @Autowired
    public ChatMessageWSServiceImpl(ChatMessageWSRepository chatMessageWSRepository) {
        this.chatMessageWSRepository = chatMessageWSRepository;
    }
}
