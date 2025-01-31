package com.song.chatpractice.stomp.service;

import com.song.chatpractice.stomp.dto.ChatMessageDto;
import com.song.chatpractice.stomp.entity.ChatMessage;
import com.song.chatpractice.stomp.repository.ChatMessageRepository;
import com.song.chatpractice.stomp.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository,
                                  SimpMessagingTemplate simpMessagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendMessage(String roomId, ChatMessageDto chatMessageDto) {

        // MongoDB에 메시지 정보 저장
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setSender(chatMessageDto.getSender());
        chatMessage.setMessage(chatMessageDto.getMessage());
        // Type은 enum 타입임으로, 넘어오는 타입의 이름을 넣어준다.
        chatMessage.setType(ChatMessage.MessageType.valueOf(chatMessageDto.getMessageType().name()));
        chatMessage.setSendDate(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);

        // Websocket을 통해 메시지 직접 전송 - Client(front)에서는 /topic/message/방번호 를 구독(sub)하고 있는 client만 채팅을 받음
        simpMessagingTemplate.convertAndSend("/topic/message/" + roomId, chatMessageDto);
    }


}
