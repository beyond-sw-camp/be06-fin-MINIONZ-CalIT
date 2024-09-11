package minionz.backend.scrum.dashboard;


import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.backend.scrum.dashboard.model.response.ReadMyDashboardResponse;
import minionz.backend.scrum.dashboard.model.response.ReadWorkspaceDashboardResponse;
import minionz.backend.user.model.User;
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
    public BaseResponse<ReadMyDashboardResponse> readMyDashboard(ReadMyDashboardRequest request) {
        User user = User.builder().userId(1L).build();
        ReadMyDashboardResponse response;

        try {
            response = dashboardService.readMyDashboard(user, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_DASHBOARD_READ_SUCCESS, response);
    }

    @GetMapping("/workspace/{workspaceId}")
    public BaseResponse<ReadWorkspaceDashboardResponse> readWorkspaceDashboard(@PathVariable Long workspaceId, ReadMyDashboardRequest request) {
        ReadWorkspaceDashboardResponse response;

        try {
            response = dashboardService.readWorkspaceDashboard(workspaceId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_DASHBOARD_READ_SUCCESS, response);
    }
}
