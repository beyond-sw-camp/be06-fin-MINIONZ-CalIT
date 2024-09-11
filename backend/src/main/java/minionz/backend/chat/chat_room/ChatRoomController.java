package minionz.backend.chat.chat_room;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_room.model.request.CreateChatRoomRequest;
import minionz.backend.chat.chat_room.model.response.CreateChatRoomResponse;
import minionz.backend.chat.chat_room.model.response.ReadChatRoomResponse;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping(value = "/room")
    public BaseResponse<CreateChatRoomResponse> create(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateChatRoomRequest request) {
        User user = customUserDetails.getUser();
        CreateChatRoomResponse response = chatRoomService.create(user, request).getResult();
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_CREATE_SUCCESS, response);
    }

    @GetMapping(value = "/roomList")
    public BaseResponse<List<ReadChatRoomResponse>> readRoomList(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        List<ReadChatRoomResponse> response = chatRoomService.roomList(user.getUserId());
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_LIST_SUCCESS, response);
    }

}
