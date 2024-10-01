package minionz.backend.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.backend.scrum.meeting.model.response.ReadMeetingResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

  private final MeetingService meetingService;

  @PostMapping("/{sprintId}")
  public BaseResponse<BaseResponseStatus> createMeeting(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long sprintId,
      @RequestBody CreateMeetingRequest request) {

    try {
      meetingService.createMeeting(customUserDetails.getUser(), request, sprintId);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.MEETING_CREATE_SUCCESS);
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

}
