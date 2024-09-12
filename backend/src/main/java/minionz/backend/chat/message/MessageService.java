package minionz.backend.chat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_participation.ChatParticipationRepository;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.chat.chat_room.model.response.ReadMessageResponse;
import minionz.backend.chat.message.model.Message;
import minionz.backend.chat.message.model.MessageStatus;
import minionz.backend.chat.message.model.MessageType;
import minionz.backend.chat.message.model.request.FileInfo;
import minionz.backend.chat.message.model.request.SendMessageRequest;
import minionz.backend.chat.message.model.request.UpdateMessageRequest;
import minionz.backend.utils.CloudFileUpload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ChatParticipationRepository chatParticipationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final CloudFileUpload cloudFileUpload;

    public void sendMessage(Long chatRoomId, SendMessageRequest request, MultipartFile[] files, Long senderId) {

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
                .messageStatus(MessageStatus.UNREAD)
                .build();

        messageRepository.save(message);

        // Kafka 로 메시지 전송
        String topic = "chat-room-" + chatRoomId.toString();

        request.setFiles(fileUrls);
        try {
            String messageStr = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(topic, senderId.toString(), messageStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void updateMessage(Long chatRoomId, UpdateMessageRequest request, Long senderId) {
        if (!request.getMessageContents().isEmpty()) {
            // 받아온 senderId로 채팅방 목록을 조회
            List<ChatParticipation> chatRoomList = chatParticipationRepository.findAllByUser_UserId(senderId);

            // 채팅방 목록에서 요청된 채팅방이 있는지 확인
            boolean isAuthorized = chatRoomList.stream()
                    .anyMatch(participation -> participation.getChatRoom().getChatRoomId().equals(chatRoomId));

            if (isAuthorized) {
                // JPQL 을 사용하여 메시지를 조회합니다.
                Message message = messageRepository.findMessageById(request.getMessageId());
                message.setMessageContents(request.getMessageContents());
                messageRepository.save(message);
            }
        }

    }

    public void deleteMessage(Long messageId, Long senderId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메시지를 찾을 수 없습니다."));
        if (!message.getChatParticipation().getUser().getUserId().equals(senderId)) {
            throw new SecurityException("본인의 메시지만 삭제할 수 있습니다.");
        }
        message.setDeletedAt(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<ReadMessageResponse> readMessage(Long chatRoomId, Long userId) {
        List<Message> messages = messageRepository.findByChatRoomIdAndDeletedAtIsNullOrderByCreatedAtAsc(chatRoomId);
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }
        return messages.stream()
                .map(message -> ReadMessageResponse.builder()
                        .messageId(message.getMessageId())
                        .senderId(userId)
                        .userName(message.getChatParticipation().getUser().getUserName())
                        .messageContents(message.getMessageContents())
                        .createdAt(message.getCreatedAt())
                        .messageType(message.getMessageType())
                        .messageStatus(message.getMessageStatus())
                        .file(FileInfo.builder()
                                .fileType(message.getFileType())
                                .fileSize(message.getFileSize())
                                .fileUrl(message.getFileUrl())
                                .fileName(message.getFileName())
                                .build())
                        .persona(message.getChatParticipation().getUser().getPersona())
                        .build())
                .collect(Collectors.toList());
    }

    @KafkaListener(topicPattern = "chat-room-.*", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        try {
            SendMessageRequest request = objectMapper.readValue(record.value(), SendMessageRequest.class);
            // STOMP 를 통해 웹소켓으로 메시지 전송
            messagingTemplate.convertAndSend("/sub/room/" + request.getChatRoomId().toString(), request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}