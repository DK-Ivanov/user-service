package org.aston.cours.kafka.producer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class TestKafkaProducerConfig {

    private static final Logger log = LoggerFactory.getLogger(TestKafkaProducerConfig.class);
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ProducerFactory<String, UserDtoKafka> producerFactory(ObjectMapper objectMapper) {
        JsonSerializer<UserDtoKafka> valueSerializer = new JsonSerializer<>(objectMapper);
        valueSerializer.setAddTypeInfo(false);

        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.getClass());

        return new DefaultKafkaProducerFactory<>(
                configProperties,
                new StringSerializer(),
                valueSerializer
        );
    }

    @Bean
    public KafkaTemplate<String, UserDtoKafka> kafkaTemplate(
            ProducerFactory<String, UserDtoKafka> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
