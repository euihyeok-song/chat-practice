package com.song.chatpractice.stomp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {

    private String roomId;
    private String sender;
    private String message;
    private MessageType type;

    public enum MessageType {
        ENTER, CHAT, LEAVE
    }
}
