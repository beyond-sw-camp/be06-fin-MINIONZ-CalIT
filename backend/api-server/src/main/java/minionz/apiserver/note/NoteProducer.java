package minionz.apiserver.note;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minionz.common.note.model.NoteMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NoteProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NoteProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendNoteUpdate(NoteMessage noteMessage) {
        try {
            String messageStr = objectMapper.writeValueAsString(noteMessage);
            kafkaTemplate.send("note-topic", noteMessage.getNoteId().toString(), messageStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to send Kafka message", e);
        }
    }
}
