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
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

    public void sendMessage(Long chatRoomId, SendMessageRequest request, MultipartFile[] files, Long senderId) throws BaseException {

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
            throw new BaseException(BaseResponseStatus.KAFKA_SEND_FAILED);
        }
    }

    public void updateMessage(Long chatRoomId, UpdateMessageRequest request, Long senderId) {
        if (request.getMessageContents().isEmpty()) {
            throw new BaseException(BaseResponseStatus.MESSAGE_CONTENT_EMPTY);
        }

        // 참여 확인
        List<ChatParticipation> chatRoomList = chatParticipationRepository.findAllByUser_UserId(senderId);
        boolean isAuthorized = chatRoomList.stream()
                .anyMatch(participation -> participation.getChatRoom().getChatRoomId().equals(chatRoomId));

        if (!isAuthorized) {
            throw new BaseException(BaseResponseStatus.CHATROOM_USER_NOT_AUTHORIZED);
        }

        // 메시지 조회 및 업데이트
        Message message = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MESSAGE_NOT_FOUND));

        message.setMessageContents(request.getMessageContents());

        try {
            messageRepository.save(message);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.MESSAGE_UPDATE_FAILED);
        }
    }

    public void deleteMessage(Long messageId, Long senderId) throws BaseException{
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MESSAGE_NOT_FOUND));
        if (!message.getChatParticipation().getUser().getUserId().equals(senderId)) {
            throw new BaseException(BaseResponseStatus.NOT_AUTHORIZED_TO_DELETE);
        }
        message.setDeletedAt(LocalDateTime.now());
        try {
            messageRepository.save(message);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.MESSAGE_DELETE_FAILED);
        }
    }

    public List<ReadMessageResponse> readMessage(Long chatRoomId, Long userId) throws BaseException {
        List<Message> messages = messageRepository.findByChatRoomIdAndDeletedAtIsNullOrderByCreatedAtAsc(chatRoomId);
        if (messages.isEmpty()) {
            throw new BaseException(BaseResponseStatus.MESSAGE_NOT_FOUND);
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
                        .isOwn(message.getChatParticipation().getUser().getUserId().equals(userId))
                        .build())
                .collect(Collectors.toList());
    }

    public void enterChatRoom(Long chatRoomId, Long userId) throws BaseException {
        List<Message> unreadMessages = messageRepository.findUnreadMessagesByChatRoomId(chatRoomId, MessageStatus.UNREAD);
        // 안 읽은 메세지가 있을 때만 상태 변경
        if (!unreadMessages.isEmpty()) {
            for (Message message : unreadMessages) {
                message.setMessageStatus(MessageStatus.READ);
                }
            }

        try {
            messageRepository.saveAll(unreadMessages);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.MESSAGE_STATUS_UPDATE_FAIL);
        }
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