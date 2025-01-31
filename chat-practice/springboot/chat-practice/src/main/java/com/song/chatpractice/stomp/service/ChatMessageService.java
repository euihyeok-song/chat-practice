package com.song.chatpractice.stomp.service;

import com.song.chatpractice.stomp.dto.ChatMessageDto;

public interface ChatMessageService {

    void sendMessage(String roomId, ChatMessageDto chatMessageDto);
}
