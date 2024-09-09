package minionz.backend.scrum.workspace;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.backend.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> createWorkspace(@RequestBody CreateWorkspaceRequest request) {

//        TODO: 추 후 AuthenticationPrincipal로 user 찾도록 수정.
        User user = User.builder().userId(1L).build();

        workspaceService.create(user, request);

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_CREATE_SUCCESS);
    }

    @GetMapping("/all")
    public BaseResponse<List<ReadWorkspaceResponse>> readAllWorkspace() {
//      TODO: 추 후 AuthenticationPrincipal로 user 찾도록 수정.
        User user = User.builder().userId(2L).build();

        List<ReadWorkspaceResponse> readWorkspaceResponses = workspaceService.readAll(user);

        return new BaseResponse<>(BaseResponseStatus.MY_WORKSPACE_READ_SUCCESS, readWorkspaceResponses);
    }
}
