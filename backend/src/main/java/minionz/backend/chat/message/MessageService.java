package minionz.backend.chat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_participation.ChatParticipationRepository;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.chat.chat_room.ChatRoomRepository;
import minionz.backend.chat.message.model.Message;
import minionz.backend.chat.message.model.MessageType;
import minionz.backend.chat.message.model.request.MessageRequest;
import minionz.backend.chat.message.model.response.MessageResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipationRepository chatParticipationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageResponse sendMessage(Long chatRoomId, MessageRequest request) {
        ChatParticipation participation = chatParticipationRepository.findByChatRoom_ChatRoomIdAndUser_UserId(chatRoomId, request.getSenderId())
                .orElseThrow(() -> new RuntimeException("ChatParticipation not found"));

        Message message = Message.builder()
                .userId(request.getSenderId())
                .chatRoomId(chatRoomId)
                .chatParticipation(participation)
                .messageContents(request.getMessageContents())
                .messageType(request.getFile() != null ? MessageType.FILE : MessageType.TEXT)
//                .fileName(request.getFile() != null ? request.getFile().getOriginalFilename() : null)
//                .fileSize(request.getFile() != null ? String.valueOf(request.getFile().getSize()) : null)
//                .fileType(request.getFile() != null ? request.getFile().getContentType() : null)
//                .fileUrl(request.getFile() != null ? "/files/" + request.getFile().getOriginalFilename() : null)
                .build();

        messageRepository.save(message);

        // Kafka 로 메시지 전송
        String topic = "chat-room-" + chatRoomId.toString();
        try {
            String messageStr = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(topic, request.getSenderId().toString(), messageStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 예외 처리
        }

        List<Long> participantIds = chatParticipationRepository.findById(chatRoomId).stream()
                .map(chatParticipation -> chatParticipation.getUser().getUserId())
                .collect(Collectors.toList());

        return MessageResponse.builder()
                .chatRoomName(message.getChatParticipation().getChatRoom().getChatRoomName())
                .participants(participantIds)
                .topicName(topic)
                .fileType(message.getFileType())
                .fileSize(message.getFileSize())
                .fileName(message.getFileName())
                .fileUrl(message.getFileUrl())
                .messageContents(message.getMessageContents())
                .messageType(message.getMessageType())
                .build();
    }

    @KafkaListener(topicPattern = "chat-room-.*", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        try {
            MessageRequest request = objectMapper.readValue(record.value(), MessageRequest.class);
            // STOMP 를 통해 웹소켓으로 메시지 전송
            messagingTemplate.convertAndSend("/sub/room/" + request.getChatRoomId().toString(), request);
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}
