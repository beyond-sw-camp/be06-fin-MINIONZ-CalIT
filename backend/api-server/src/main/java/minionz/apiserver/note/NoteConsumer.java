package minionz.apiserver.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import minionz.common.note.model.NoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NoteConsumer {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public NoteConsumer(ObjectMapper objectMapper, SimpMessagingTemplate messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "note-topic")
    public void consumeNoteMessage(ConsumerRecord<String, String> record) {
        try {
            NoteMessage noteMessage = objectMapper.readValue(record.value(), NoteMessage.class);
            // WebSocket으로 클라이언트에게 메시지 전송
            messagingTemplate.convertAndSend("/topic/note/" + noteMessage.getNoteId(), noteMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
