package minionz.backend.scrum.workspace;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> createWorkspace(@RequestBody CreateWorkspaceRequest request)  {

//        TODO: 추 후 AuthenticationPrincipal로 user 찾도록 수정.
        User user = User.builder().userId(1L).build();

        workspaceService.create(user,request);

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_CREATE_SUCCESS);
    }
}
