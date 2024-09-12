package minionz.backend.scrum.workspace;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.backend.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> createWorkspace(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails , @RequestBody CreateWorkspaceRequest request) {

        workspaceService.create(customUserDetails.getUser(), request);

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_CREATE_SUCCESS);
    }

    @GetMapping("/all")
    public BaseResponse<List<ReadWorkspaceResponse>> readAllWorkspace(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

        List<ReadWorkspaceResponse> readWorkspaceResponses = workspaceService.readAll(customUserDetails.getUser());

        return new BaseResponse<>(BaseResponseStatus.MY_WORKSPACE_READ_SUCCESS, readWorkspaceResponses);
    }
}
