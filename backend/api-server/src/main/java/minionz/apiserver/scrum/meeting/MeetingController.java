package minionz.apiserver.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.apiserver.scrum.meeting.model.response.CreateMeetingResponse;
import minionz.apiserver.scrum.meeting.model.response.ReadAllMeetingResponse;
import minionz.apiserver.scrum.meeting.model.response.ReadMeetingResponse;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/{sprintId}")
    public BaseResponse<CreateMeetingResponse> createMeeting(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long sprintId,
            @RequestBody CreateMeetingRequest request) {

        CreateMeetingResponse response;

        try {
            response = meetingService.createMeeting(customUserDetails.getUser(), request, sprintId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MEETING_CREATE_SUCCESS, response);
    }

    @GetMapping("/{workspaceId}/{meetingId}")
    public BaseResponse<ReadMeetingResponse> readMeeting(@PathVariable Long meetingId) {
        ReadMeetingResponse response;
        try {
            response = meetingService.readMeeting(meetingId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MEETING_READ_SUCCESS, response);
    }

    //회의 전체 조회
    @GetMapping("/{workspaceId}/search-all")
    public BaseResponse<Page<ReadAllMeetingResponse>> readAllMeeting(@RequestParam int page, @RequestParam int size) {
        Page<ReadAllMeetingResponse> response = meetingService.readAll(page, size);
        return new BaseResponse<>(BaseResponseStatus.MEETING_READ_SUCCESS, response);
    }

}
