package minionz.backend.scrum.sprint;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.sprint.model.request.CreateSprintRequest;
import minionz.backend.scrum.sprint.model.response.ReadAllSprintResponse;
import minionz.backend.scrum.sprint.model.response.ReadSprintResponse;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sprint")
public class SprintController {

    private final SprintService sprintService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> craeteSprint(@RequestBody CreateSprintRequest request) {
        User user = User.builder().userId(1L).build();

        try {
            sprintService.createSprint(user, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_CREATE_SUCCESS);
    }

    @GetMapping("/{sprintId}")
    public BaseResponse<ReadSprintResponse> readSprint(@PathVariable Long sprintId) {
        User user = User.builder().userId(1L).build();
        ReadSprintResponse response;

        try {
            response = sprintService.readSprint(user, sprintId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_READ_SUCCESS, response);
    }

    @GetMapping("all/{workspaceId}")
    public BaseResponse<List<ReadAllSprintResponse>> readAllSprint(@PathVariable Long workspaceId) {
        User user = User.builder().userId(1L).build();
        List<ReadAllSprintResponse> response;

        try {
            response = sprintService.readAllSprint(user, workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_READ_ALL_SUCCESS, response);
    }
}
