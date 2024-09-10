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
import minionz.backend.utils.CloudFileUpload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudFileUpload cloudFileUpload;

    public MessageResponse sendMessage(Long chatRoomId, MessageRequest request, MultipartFile[] files, Long senderId) {

        ChatParticipation participation = chatParticipationRepository.findByChatRoom_ChatRoomIdAndUser_UserId(chatRoomId, senderId);

        // 파일 업로드 처리
        List<String> fileUrls = null;
        if (files != null) {
            fileUrls = cloudFileUpload.multipleUpload(files);
        }

        Message message = Message.builder()
                .userId(senderId)
                .chatRoomId(chatRoomId)
                .chatParticipation(participation)
                .messageContents(request.getMessageContents())
                .messageType(fileUrls != null ? MessageType.FILE : MessageType.TEXT)
                .fileName(files != null ? files[0].getOriginalFilename() : null)
                .fileSize(files != null ? String.valueOf(files[0].getSize()) : null)
                .fileType(files != null ? files[0].getContentType() : null)
                .fileUrl(fileUrls != null && !fileUrls.isEmpty() ? fileUrls.get(0) : null)
                .build();

        Message savedMessage = messageRepository.save(message);

        // Kafka 로 메시지 전송
        String topic = "chat-room-" + chatRoomId.toString();

        request.setFiles(fileUrls);
        try {
            String messageStr = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(topic, senderId.toString(), messageStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 예외 처리
        }

        // 참가자들 목록
        List<Long> participantIds = chatParticipationRepository.findByChatRoom_ChatRoomId(chatRoomId).stream()
                .map(chatParticipation -> chatParticipation.getUser().getUserId())
                .collect(Collectors.toList());

        return MessageResponse.builder()
                .chatRoomName(savedMessage.getChatParticipation().getChatRoom().getChatRoomName())
                .participants(participantIds)
                .topicName(topic)
                .fileType(savedMessage.getFileType())
                .fileSize(savedMessage.getFileSize())
                .fileName(savedMessage.getFileName())
                .fileUrl(savedMessage.getFileUrl())
                .messageContents(savedMessage.getMessageContents())
                .messageType(savedMessage.getMessageType())
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
