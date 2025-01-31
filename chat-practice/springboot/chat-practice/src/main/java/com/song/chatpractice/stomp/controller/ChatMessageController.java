package com.song.chatpractice.stomp.controller;

import com.song.chatpractice.stomp.dto.ChatMessageDto;
import com.song.chatpractice.stomp.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("chatStompController")
@RequestMapping("/stomp/chat")
@Slf4j
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Websocket 으로 부터 넘어오는 메시지 처리
    @MessageMapping("{roomId}")
    // @DestonationVariable은 MessageMapping에서 전송되는 URL에서 roomId를 뺴오는 역할을 한다. (@GetMapping - @Pathvariable과 동일)
    public void sendMessage(@DestinationVariable String roomId, ChatMessageDto chatMessageDto
                            // Websocket 세션 정보를 관리하는 객체 ( 주로 사용자 인증 정보 or 세션 데이터)
                            // 서버 측에서 Websocket 세션을 통해 자동으로 관리하는 객체로 request시 특정 값을 넣어줄 필요 X
                            ,SimpMessageHeaderAccessor simpMessageHeaderAccessor){

        if (ChatMessageDto.MessageType.ENTER.equals(chatMessageDto.getMessageType())) {
            // 새로 들어온 클라이언트이기 때문에, Websocket의 세션에 클라이언드의 이름과 채팅방 번호를 저장한다.
            simpMessageHeaderAccessor.getSessionAttributes().put("username", chatMessageDto.getSender());
            simpMessageHeaderAccessor.getSessionAttributes().put("roomId",chatMessageDto.getRoomId());
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장하셨습니다.");
        }

        chatMessageService.sendMessage(roomId, chatMessageDto);
    }
}
