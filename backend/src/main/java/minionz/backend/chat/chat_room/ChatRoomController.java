package minionz.backend.chat.chat_room;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_room.model.request.CreateChatRoomRequest;
import minionz.backend.chat.chat_room.model.request.SearchChatRoomRequest;
import minionz.backend.chat.chat_room.model.request.UpdateChatRoomRequest;
import minionz.backend.chat.chat_room.model.response.CreateChatRoomResponse;
import minionz.backend.chat.chat_room.model.response.ReadChatRoomResponse;
import minionz.backend.chat.chat_room.model.response.SearchChatRoomResponse;
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

    @GetMapping(value = "/{workspaceId}/roomList")
    public BaseResponse<List<ReadChatRoomResponse>> readRoomList(@PathVariable Long workspaceId, @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        List<ReadChatRoomResponse> response = chatRoomService.roomList(user.getUserId(), workspaceId);
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_LIST_SUCCESS, response);
    }

    @PatchMapping(value = "/room/{chatRoomId}/edit")
    public BaseResponse<BaseResponseStatus> updateRoomName(@PathVariable Long chatRoomId, @RequestBody UpdateChatRoomRequest request, @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        BaseResponse<BaseResponseStatus> response = chatRoomService.updateChatRoomName(chatRoomId, request, user);
        return response;
    }

    @DeleteMapping("/{chatRoomId}/exit")
    public BaseResponse<BaseResponseStatus> exitChatRoom(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails,
                                             @PathVariable Long chatRoomId) {
        User user = customUserDetails.getUser();
        BaseResponse<BaseResponseStatus> response = chatRoomService.exitChatRoom(chatRoomId, user);
        return response;
    }

    @GetMapping("/search")
    public BaseResponse<List<SearchChatRoomResponse>> searchChatRooms(@RequestBody SearchChatRoomRequest request) {
        List<SearchChatRoomResponse> response = chatRoomService.searchRoomList(request);
        return new BaseResponse<>(BaseResponseStatus.CHATROOM_SEARCH_SUCCESS, response);
    }


}
