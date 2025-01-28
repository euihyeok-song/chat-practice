package com.song.chatpractice.websocket.dto;

import com.song.chatpractice.websocket.entity.ChatMessageWS;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoomWSDTO {

    // 채팅방 번호
    private String roomId;

    // 채팅방 이름
    private String name;

    // 입장한 클라이언트의 정보 ( WebsocketSession 정보 list )
    private Set<WebSocketSession> sessions = new HashSet<>();

    public void handleActions(WebSocketSession session, ChatMessageWSDTO chatMessageWSDTO, CHatService)
}
