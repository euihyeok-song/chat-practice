package com.song.chatpractice.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/* 설명.
    WebSocket Handler 추가
    Socket은 서버와 클라이언트가 1:N 관계로 연걸되기 때문에, 힌 서버에 여러 클라이언트들 접속하여 전송한 메세지를 처리해줄 Handler 필요
    TextWebSocketHandler를 상속받아서 Handler 생성
 */

@Component
@Slf4j
public class WebSocketChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // Client가 전송한 Message의 본문을 담는다. (json형태)
        String payload = message.getPayload();
        // 사용자가 입력한 문자열이 찍힘 (Ex. hello )
        log.info(payload);

        // Client와 Server가 연결되어 있는 객체를 "session"이라고 하고, 아래는 session을 통해 서버가 클라이언트에게 보낼 메시지를 담는다.
        TextMessage textMesaage = new TextMessage("Hello World!");
        session.sendMessage(textMesaage);


    }
}
