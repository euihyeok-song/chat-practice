package com.song.chatpractice.kafka.config;

import com.song.chatpractice.kafka.dto.ChatMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String cons_bootstrap_server;

    @Value("${spring.kafka.consumer.group-id}")
    private String cons_group_id;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String cons_auto_offset_reset;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String cons_key_deserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String cons_value_deserializer;

    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    // Kafka 메시지 수신하는 Consumer 객체 생성
    @Bean
    public ConsumerFactory<String, ChatMessageDto> consumerFactory(){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cons_bootstrap_server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, cons_group_id);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, cons_auto_offset_reset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, cons_key_deserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, cons_value_deserializer);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    // Kafka 메시지를 수신 & 처리하는 리스너 컨테이너
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafkaListenerContainerFactory;
    }
}
