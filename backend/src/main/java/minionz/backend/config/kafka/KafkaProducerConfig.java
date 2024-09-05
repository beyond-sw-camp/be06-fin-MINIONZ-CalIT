package minionz.backend.config.kafka;

import minionz.backend.chat.message.model.request.MessageRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
    // ProducerFactory 와 KafkaTemplate 을 설정하여 Kafka 에 메시지를 전송할 준비

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaBroker;

    @Bean
    // 2) 아래에서 구성한대로 Kafka Producer 를 생성해서 3으로 준다
    public ProducerFactory<String, MessageRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    // 1) Kafka 프로듀서에 필요한 설정들을 HashMap 으로 정의해서 put 으로 추가해서 2로 전달
    public Map<String, Object> kafkaProducerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    @Bean
    // 3) producerFactory 를 사용하여 생성한 것을 kafka 토픽을 통해 메세지를 전송
    public KafkaTemplate<String, MessageRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

