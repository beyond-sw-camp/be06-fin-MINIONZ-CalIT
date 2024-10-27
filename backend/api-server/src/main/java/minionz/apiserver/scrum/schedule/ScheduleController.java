package minionz.apiserver.scrum.schedule;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.schedule.request.ReadScheduleRequest;
import minionz.apiserver.scrum.schedule.response.ReadMonthlyResponse;
import minionz.apiserver.scrum.schedule.response.ReadWeeklyResponse;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/{workspaceId}/monthly")
  public BaseResponse<ReadMonthlyResponse> readWorkspaceMonthly(@PathVariable Long workspaceId,
      ReadScheduleRequest request) {

    ReadMonthlyResponse response;

    try {
      response = scheduleService.readWorkspaceMonthly(workspaceId, request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.WORKSPACE_MONTHLY_READ_SUCCESS, response);
  }

  @GetMapping("/my/monthly")
  public BaseResponse<ReadMonthlyResponse> readMyspaceMonthly(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, ReadScheduleRequest request) {

    ReadMonthlyResponse response;

    try {
      response = scheduleService.readMyspaceMonthly(customUserDetails.getUser(), request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.MY_MONTHLY_READ_SUCCESS, response);
  }

  @GetMapping("/{workspaceId}/weekly")
  public BaseResponse<ReadWeeklyResponse> readWorkspaceWeekly(@PathVariable Long workspaceId,
      ReadScheduleRequest request) {

    ReadWeeklyResponse response;

    try {
      response = scheduleService.readWorkspaceWeekly(workspaceId, request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.WORKSPACE_WEEKLY_READ_SUCCESS, response);
  }

  @GetMapping("/my/weekly")
  public BaseResponse<ReadWeeklyResponse> readMyspaceWeekly(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, ReadScheduleRequest request) {

    ReadWeeklyResponse response;

    try {
      response = scheduleService.readMyspaceWeekly(customUserDetails.getUser(), request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.MY_WEEKLY_READ_SUCCESS, response);
  }
}
