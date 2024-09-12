package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.scrum.label.model.response.ReadLabelResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;

    @PostMapping("/sprint")
    public BaseResponse<BaseResponseStatus> createSprintLabel(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateLabelRequest request) {

        try {
            labelService.createSprintLabel(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/sprint")
    public BaseResponse<List<ReadLabelResponse>> readSprintLabel(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestParam Long id) {
        List<ReadLabelResponse> response;

        try {
            response = labelService.readSprintLabel(customUserDetails.getUser(), id);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

    @PostMapping("/task")
    public BaseResponse<BaseResponseStatus> createTaskLabel(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateLabelRequest request) {

        try {
            labelService.createTaskLabel(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/task")
    public BaseResponse<List<ReadLabelResponse>> readTaskLabel(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestParam Long id) {

        List<ReadLabelResponse> response;

        try {
            response = labelService.readTaskLabel(customUserDetails.getUser(), id);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

}
