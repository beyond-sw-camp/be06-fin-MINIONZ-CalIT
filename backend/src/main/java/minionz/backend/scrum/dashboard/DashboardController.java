package minionz.backend.scrum.dashboard;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.backend.scrum.dashboard.model.response.ReadBurndownResponse;
import minionz.backend.scrum.dashboard.model.response.ReadMyDashboardResponse;
import minionz.backend.scrum.dashboard.model.response.ReadWorkspaceDashboardResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/my")
  public BaseResponse<ReadMyDashboardResponse> readMyDashboard(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, ReadMyDashboardRequest request) {
    ReadMyDashboardResponse response;

    try {
      response = dashboardService.readMyDashboard(customUserDetails.getUser(), request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.MY_DASHBOARD_READ_SUCCESS, response);
  }

  @GetMapping("/{workspaceId}")
  public BaseResponse<ReadWorkspaceDashboardResponse> readWorkspaceDashboard(@PathVariable Long workspaceId,
      ReadMyDashboardRequest request) {
    ReadWorkspaceDashboardResponse response;

    try {
      response = dashboardService.readWorkspaceDashboard(workspaceId, request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.MY_DASHBOARD_READ_SUCCESS, response);
  }

  @GetMapping("/{workspaceId}/burndown")
  public BaseResponse<ReadBurndownResponse> readBurndownChart(@PathVariable Long workspaceId) {
    ReadBurndownResponse response;

    try {
      response = dashboardService.readBurndownChart(workspaceId);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.WORKSPACE_DASHBOARD_READ_SUCCESS, response);
  }
}
