package minionz.apiserver.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import minionz.common.note.model.NoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class NoteConsumer {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaBroker;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public NoteConsumer(KafkaTemplate<String, String> kafkaTemplate, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }


    public void consumeNoteById(Long noteId) {
        String topicName = "note-topic-" + noteId;

        // 수동으로 컨슈머 생성 및 구독
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBroker);
        props.put("group.id", "note-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        // 메시지 수신 후 WebSocket으로 전송
        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        NoteMessage noteMessage = objectMapper.readValue(record.value(), NoteMessage.class);
                        messagingTemplate.convertAndSend("/topic/note/" + noteId, noteMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

