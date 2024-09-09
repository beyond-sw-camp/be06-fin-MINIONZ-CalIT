package minionz.backend.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/{sprintId}")
    public BaseResponse<BaseResponseStatus> createMeeting(@PathVariable Long sprintId, @RequestBody CreateMeetingRequest request){
        User user = User.builder().userId(1L).build();

        try{
            meetingService.createMeeting(user,request,sprintId);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MEETING_CREATE_SUCCESS);
    }
}
