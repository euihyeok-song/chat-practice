package com.song.chatpractice.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-chat")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 메시지를 발행(송신 - publish)할때 사용하는 prefix 설정 - /queue는 1대1 , /topic은 1대다 채팅방을 의미
//        registry.setApplicationDestinationPrefixes("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/topic");

        // 메시지를 수신(subscribe)할때 사용하는 prefix 설정
        registry.enableSimpleBroker("/app");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(8192)
                .setSendTimeLimit(15 * 1000)
                .setSendBufferSizeLimit(3 * 512 * 1024);
    }
}
