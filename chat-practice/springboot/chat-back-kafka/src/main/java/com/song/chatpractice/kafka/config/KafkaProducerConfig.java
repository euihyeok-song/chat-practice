package com.song.chatpractice.kafka.config;

import com.song.chatpractice.kafka.dto.ChatMessageDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String prod_bootstrap_server;

    @Value("${spring.kafka.producer.key-serializer}")
    private String prod_key_serializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String prod_value_serializer;

    @Autowired
    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    // Kafka에서 메시지를 생성하는 역할을 하는 Producer 객체 생성
    @Bean
    public ProducerFactory<String, ChatMessageDto> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, prod_bootstrap_server);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, prod_key_serializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, prod_value_serializer);
        return new DefaultKafkaProducerFactory<>(config);
    }

    // Kafka Producer 객체 템플릿
    @Bean
    public KafkaTemplate<String, ChatMessageDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
