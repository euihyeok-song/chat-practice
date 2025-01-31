package com.song.chatpractice.stomp.service;

import com.song.chatpractice.stomp.dto.ChatRoomDto;
import com.song.chatpractice.stomp.entity.ChatRoom;
import com.song.chatpractice.stomp.repository.ChatRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatMessageService chatMessageService,
                               SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageService = chatMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public List<ChatRoomDto> getAllRooms() {

        return chatRoomRepository.findAllRooms();
    }

    @Override
    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(chatRoom.getName());
        chatRoom.setParticipants(chatRoom.getParticipants());

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom leaveChatRoom(String roomId, String userId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        // 채팅방 참여 인원에서 나가는 사람 리스트에서 나가는 사람 삭제
        List<String> participants = new ArrayList<>(chatRoom.getParticipants());
        participants.remove(userId);
        chatRoom.setParticipants(participants);

        // 채팅방 나가는 시점
        LocalDateTime leaveTime = LocalDateTime.now();

        // 해당 사용자가 나갔음을 채팅방에 뿌려주기 위한 Message 저장 ( 예외 처리 필요 )
        chatMessageService.leaveMessage(roomId, userId, leaveTime);

        // 수정된 채팅방 정보 저장
        ChatRoom updateChatRoom = chatRoomRepository.save(chatRoom);

        // 채팅방에 있는 사용자들에게 업데이트 된 채팅방 정보 전송
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId + "/update" + updateChatRoom);

        return updateChatRoom;
    }


}
