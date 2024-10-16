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
            String topicName = "note-topic-" + noteMessage.getNoteId(); // noteId 별로 다른 토픽 생성
            String message = objectMapper.writeValueAsString(noteMessage);
            kafkaTemplate.send(topicName, message); // 동적으로 생성된 토픽으로 메시지 전송
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing NoteMessage", e);
        }
    }

}
