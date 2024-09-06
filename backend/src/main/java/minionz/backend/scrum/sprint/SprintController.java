package minionz.backend.scrum.sprint;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.sprint.model.request.CreateSprintRequest;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sprint")
public class SprintController {

    private final SprintService sprintService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> craeteSprint(@RequestBody CreateSprintRequest request){
        User user = User.builder().userId(1L).build();

        sprintService.createSprint(user, request);

        return new BaseResponse<>(BaseResponseStatus.SPRINT_CREATE_SUCCESS);
    }
}
