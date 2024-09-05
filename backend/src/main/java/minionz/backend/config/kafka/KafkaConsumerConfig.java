package minionz.backend.config.kafka;

import minionz.backend.chat.message.model.request.MessageRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaBroker;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageRequest> kafkaListenerContainerFactory() {
        // 카프카에서 비동기적으로 메세지를 수신하는 리스너 컨테이너를 생성.
        // 아래에서 생성 된 consumerFactory 를 설정한다.
        ConcurrentKafkaListenerContainerFactory<String, MessageRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // consumerFactory 를 설정하는 곳 -> 나중에 수신하는 메소드가 있는 곳으로 간다.
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageRequest> consumerFactory() {
        // kafka 에서 메세지를 가져오는 역할을 한다 -> string 타입의 키와 MessageRequest 타입의 값을 가진
        // 메세지를 처리할 수 있는 consumerFactory 를 생성.
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(MessageRequest.class));
    }

    @Bean
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> config = new HashMap<>();
        // hashmap 을 통해서 빈 설정 맵을 만들어서 kafka 에 필요한 설정들을 put 하기.

        JsonDeserializer<MessageRequest> deserializer = new JsonDeserializer<>(MessageRequest.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return config;
    }
}

