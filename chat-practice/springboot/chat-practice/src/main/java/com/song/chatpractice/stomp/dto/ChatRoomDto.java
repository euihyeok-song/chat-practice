package com.song.chatpractice.stomp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomDto {

    private String id;
    private String name;
    private List<String> participants;
}
