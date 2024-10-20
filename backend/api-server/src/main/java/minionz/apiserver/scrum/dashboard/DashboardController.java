package minionz.apiserver.scrum.dashboard;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.apiserver.scrum.dashboard.model.response.ReadBurndownResponse;
import minionz.apiserver.scrum.dashboard.model.response.ReadMyDashboardResponse;
import minionz.apiserver.scrum.dashboard.model.response.ReadWorkspaceDashboardResponse;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
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
  public BaseResponse<ReadWorkspaceDashboardResponse> readWorkspaceDashboard(@PathVariable Long workspaceId) {
    ReadWorkspaceDashboardResponse response;

    try {
      response = dashboardService.readWorkspaceDashboard(workspaceId);
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
