package minionz.backend.chat.chat_room;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_participation.ChatParticipationRepository;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.chat.chat_room.model.ChatRoom;
import minionz.backend.chat.chat_room.model.request.CreateChatRoomRequest;
import minionz.backend.chat.chat_room.model.response.CreateChatRoomResponse;
import minionz.backend.chat.chat_room.model.response.ReadChatRoomResponse;
import minionz.backend.chat.message.MessageRepository;
import minionz.backend.chat.message.model.Message;
import minionz.backend.chat.message.model.MessageStatus;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.UserRepository;
import minionz.backend.user.model.User;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipationRepository chatParticipationRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaAdmin kafkaAdmin;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public BaseResponse<CreateChatRoomResponse> create(User user, CreateChatRoomRequest request) {
        // 로그인한 사용자가 채팅 참여에 포함되지 않았을시에 추가
        if (!request.getParticipants().contains(user.getUserId())) {
            request.getParticipants().add(user.getUserId());
        }

        String chatRoomName;
        // 본인을 추가해서 총 2명일 경우
        if (request.getParticipants().size() == 2) {
            // 개인 채팅의 경우 상대방의 이름으로 설정
            User participant = userRepository.findById(request.getParticipants().get(0))
                    .orElse(null);
            if (participant == null) {
                return new BaseResponse<>(BaseResponseStatus.CHAT_PARTICIPATION_NOT_FOUND);
            }
            chatRoomName = participant.getUserName(); // 개인 채팅방의 이름 설정
        } else {
            // 그룹 채팅의 경우 요청에서 받은 이름으로 설정
            chatRoomName = request.getChatRoomName();
        }

        // 채팅방 생성
        ChatRoom chatRoom = createChatRoom(chatRoomName);
        BaseResponse<BaseResponseStatus> participantResponse = saveChatParticipants(chatRoom, request.getParticipants());
        if (!participantResponse.getSuccess()) {
            return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_FAIL);
        }

        String chatRoomIdStr = chatRoom.getChatRoomId().toString();

        // Kafka 토픽과 메시지 전송
        createKafkaTopic(chatRoomIdStr);
//        sendCreationMessage(chatRoomIdStr, "채팅방이 생성되었습니다.");

        CreateChatRoomResponse chatRoomResponse = buildCreateChatRoomResponse(chatRoom, request.getParticipants(), chatRoomIdStr);
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_SUCCESS, chatRoomResponse);
    }

    // 채팅룸 리스트 조회
    public List<ReadChatRoomResponse> roomList(Long userId, Long workspaceId) {
        List<ChatParticipation> chatParticipations = chatParticipationRepository.findByUser_UserId(userId);

        List<ReadChatRoomResponse> responseList = new ArrayList<>();

        for (ChatParticipation participation : chatParticipations) {
            ChatRoom chatRoom = participation.getChatRoom();

            // 채팅방에서 가장 최근 메시지를 가져옴
            Message latestMessage = findLatestMessage(chatRoom.getChatRoomId());

            // 읽지 않은 메시지 수 계산
            Long unreadMessagesCount = messageRepository.countUnreadMessagesByChatRoomIdAndUserId(
                    chatRoom.getChatRoomId(), userId, MessageStatus.UNREAD);

            ReadChatRoomResponse response = ReadChatRoomResponse.builder()
                    .workspaceId(workspaceId)
                    .chatroomId(chatRoom.getChatRoomId())
                    .chatRoomName(chatRoom.getChatRoomName())
                    .messageContents(latestMessage != null ? latestMessage.getMessageContents() : "채팅방에 메세지가 없습니다.")
                    .createdAt(latestMessage != null ? latestMessage.getCreatedAt() : chatRoom.getCreatedAt())
                    .UnreadMessages(unreadMessagesCount != null ? unreadMessagesCount.intValue() : 0)
                    .build();

            responseList.add(response);
        }
        return responseList;
    }

    private ChatRoom createChatRoom(String chatRoomName) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    private BaseResponse<BaseResponseStatus> saveChatParticipants(ChatRoom chatRoom, List<Long> participantIds) {
        for (Long participantId : participantIds) {
            User participant = userRepository.findById(participantId)
                    .orElse(null); // User가 null인 경우를 처리하기 위해
            if (participant == null) {
                return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_FAIL);
            }
            ChatParticipation chatParticipation = ChatParticipation.builder()
                    .user(participant)
                    .chatRoom(chatRoom)
                    .build();
            chatParticipationRepository.save(chatParticipation);
        }
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_SUCCESS);
    }

    private CreateChatRoomResponse buildCreateChatRoomResponse(ChatRoom chatRoom, List<Long> participants, String chatRoomIdStr) {
        return CreateChatRoomResponse.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomName(chatRoom.getChatRoomName())
                .participants(participants)
                .topic("chat-room-" + chatRoomIdStr)
                .creationMessage("채팅방이 생성되었습니다.")
                .build();
    }

    // 최신 메세지 하나만 가져오기
    public Message findLatestMessage(Long chatRoomId) {
        List<Message> messages = messageRepository.findLatestMessagesByChatRoomId(chatRoomId);
        return messages.isEmpty() ? null : messages.get(0);
    }

    // 토픽 생성
    private void createKafkaTopic(String chatRoomId) {
        String topic = "chat-room-" + chatRoomId;
        NewTopic newTopic = TopicBuilder.name(topic).build();
        kafkaAdmin.createOrModifyTopics(newTopic);
    }

    // 메세지 전송
    private void sendCreationMessage(String chatRoomId, String message) {
        String topic = "chat-room-" + chatRoomId; // 채팅방 관련 토픽
        kafkaTemplate.send(topic, message); // 메시지를 토픽으로 전송
    }


}
