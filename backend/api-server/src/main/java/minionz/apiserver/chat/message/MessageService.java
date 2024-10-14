package minionz.apiserver.chat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.chat.chat_participation.ChatParticipationRepository;
import minionz.apiserver.chat.chat_room.model.response.FileInfoResponse;
import minionz.apiserver.chat.chat_room.model.response.ReadMessageResponse;
import minionz.apiserver.chat.message.model.request.FileInfo;
import minionz.apiserver.chat.message.model.request.SendMessageRequest;
import minionz.apiserver.chat.message.model.request.UpdateMessageRequest;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.utils.CloudFileUpload;
import minionz.common.chat.chat_participation.model.ChatParticipation;
import minionz.common.chat.message.model.Message;
import minionz.common.chat.message.model.MessageStatus;
import minionz.common.chat.message.model.MessageType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        FileInfo file = null;


        List<String> fileUrls = null;
        if (files != null) {
            fileUrls = cloudFileUpload.multipleUpload(files);
            file = FileInfo.builder()
                    .fileName(files[0].getOriginalFilename())
                    .fileUrl(fileUrls.get(0))
                    .fileSize(""+files[0].getSize())
                    .fileType(files[0].getContentType())
                    .build();
            request.setFile(file);
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

    public List<ReadMessageResponse> readMessage(Long chatRoomId, Long userId, Integer page, Integer size) throws BaseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = messageRepository.findByChatRoomIdAndDeletedAtIsNull(chatRoomId, pageable);

        if (messages.isEmpty()) {
            throw new BaseException(BaseResponseStatus.MESSAGE_NOT_FOUND);
        }

        return messages.stream()
                .map(message -> ReadMessageResponse.builder()
                        .messageId(message.getMessageId())
                        .senderId(message.getUserId())
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

    public List<FileInfoResponse> getFileList(Long chatRoomId) throws BaseException {
        // 파일 타입 메시지만 조회
        List<FileInfoResponse> fileInfoResponses = messageRepository.findFilesByChatRoomId(chatRoomId)
                .stream()
                .map(message -> FileInfoResponse.builder()
                        .fileName(message.getFileName())
                        .fileUrl(message.getFileUrl())
                        .fileSize(message.getFileSize())
                        .fileType(message.getFileType())
                        .build())
                .collect(Collectors.toList());

        if (fileInfoResponses.isEmpty()) {
            throw new BaseException(BaseResponseStatus.FILE_NOT_FOUND);
        }

        return fileInfoResponses;
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


    @KafkaListener(topicPattern = "chat-room-.*")
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