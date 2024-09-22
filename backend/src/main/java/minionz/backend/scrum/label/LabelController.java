package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.scrum.label.model.response.ReadLabelResponse;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/sprint")
    public BaseResponse<List<ReadLabelResponse>> readSprintLabel(@RequestParam Long id) {
        User user = User.builder().userId(1L).build();
        List<ReadLabelResponse> response = new ArrayList<>();

        try {
            response = labelService.readSprintLabel(user,id);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
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

    @GetMapping("/task")
    public BaseResponse<List<ReadLabelResponse>> readTaskLabel(@RequestParam Long id) {
        User user = User.builder().userId(1L).build();
        List<ReadLabelResponse> response = new ArrayList<>();

        try {
            response = labelService.readTaskLabel(user,id);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }
    @PostMapping("/note")
    public BaseResponse<BaseResponseStatus> createNoteLabel(@RequestBody CreateLabelRequest request) {
        User user = User.builder().userId(1L).build();
        try {
            labelService.createNoteLabel(user,request);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(BaseResponseStatus.NOTE_LABEL_CREATE_SUCCESS);

    }

    @GetMapping("/note")
    public BaseResponse<List<ReadLabelResponse>> readNoteLabel(@RequestParam Long id) {
        User user = User.builder().userId(1L).build();
        List<ReadLabelResponse> response = new ArrayList<>();

        try{
            response = labelService.readNoteLabel(user,id);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(BaseResponseStatus.NOTE_LABEL_SEARCH_SUCCESS, response);
    }

}

