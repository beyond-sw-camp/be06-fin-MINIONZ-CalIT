package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;

    @PostMapping("/sprint")
    public BaseResponse<BaseResponseStatus> createSprintLabel(@RequestBody CreateLabelRequest request) {
        User user = User.builder().userId(1L).build();

        try {
            labelService.createSprintLabel(user,request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_CREATE_SUCCESS);
    }

    @PostMapping("/task")
    public BaseResponse<BaseResponseStatus> createTaskLabel(@RequestBody CreateLabelRequest request) {
        User user = User.builder().userId(1L).build();

        try {
            labelService.createTaskLabel(user, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_LABEL_CREATE_SUCCESS);
    }

}
