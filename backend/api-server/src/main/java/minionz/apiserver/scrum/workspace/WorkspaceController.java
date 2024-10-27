package minionz.apiserver.scrum.workspace;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.task.TaskService;
import minionz.apiserver.scrum.task.model.response.ReadAllTaskResponse;
import minionz.apiserver.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.apiserver.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.apiserver.user.UserService;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final TaskService taskService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> createWorkspace(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateWorkspaceRequest request) throws JsonProcessingException {

        workspaceService.create(customUserDetails.getUser(), request);

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_CREATE_SUCCESS);
    }

    @PatchMapping("/accept/{workspaceId}")
    public BaseResponse<BaseResponseStatus> acceptWorkspace(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long workspaceId, HttpServletResponse response) {

        try {
            workspaceService.accept(customUserDetails.getUser(), workspaceId);
            userService.sendNewToken(customUserDetails, response);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_ACCEPT_SUCCESS);
    }

    @DeleteMapping("/accept/{workspaceId}")
    public BaseResponse<BaseResponseStatus> denyWorkspace(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long workspaceId) {

        try {
            workspaceService.deny(customUserDetails.getUser(), workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_DENY_SUCCESS);
    }

    @GetMapping("/my/all")
    public BaseResponse<List<ReadWorkspaceResponse>> readAllWorkspace(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

        List<ReadWorkspaceResponse> readWorkspaceResponses = workspaceService.readAll(customUserDetails.getUser());

        return new BaseResponse<>(BaseResponseStatus.MY_WORKSPACE_READ_SUCCESS, readWorkspaceResponses);
    }

    @GetMapping("/{workspaceId}/tasks")
    public BaseResponse<List<ReadAllTaskResponse>> readAllWorkspaceTask(@PathVariable Long workspaceId) {

        List<ReadAllTaskResponse> response;

        try {
            response = taskService.readAllWorkspaceTask(workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALLWORKSPACE_SUCCESS, response);
    }

}
