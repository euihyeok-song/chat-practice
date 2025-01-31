package com.song.chatpractice.stomp.service;

import com.song.chatpractice.stomp.dto.ChatMessageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageService {

    void sendMessage(String roomId, ChatMessageDto chatMessageDto);

    ChatMessageDto leaveMessage(String roomId, String userId, LocalDateTime leaveTime);

    List<ChatMessageDto> getRoomMessages(String roomId, String userId);

    Boolean isFirstJoin(String roomId, String userId);
}
