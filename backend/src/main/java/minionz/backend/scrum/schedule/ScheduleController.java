package minionz.backend.scrum.schedule;


import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.schedule.request.ReadScheduleRequest;
import minionz.backend.scrum.schedule.response.ReadMonthlyResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/workspace/{workspaceId}/monthly")
    public BaseResponse<ReadMonthlyResponse> readWorkspaceMonthly(@PathVariable Long workspaceId, ReadScheduleRequest request) {

        ReadMonthlyResponse response;

        try {
            response = scheduleService.readWorkspaceMonthly(workspaceId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_MONTHLY_READ_SUCCESS, response);
    }
}
