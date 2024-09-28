package minionz.backend.scrum.task;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.task.model.request.CreateTaskRequest;
import minionz.backend.scrum.task.model.request.UpdateTaskStatusRequest;
import minionz.backend.scrum.task.model.response.ReadAllTaskResponse;
import minionz.backend.scrum.task.model.response.ReadTaskResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/{sprintId}")
    public BaseResponse<BaseResponseStatus> createTask(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateTaskRequest request) {

        try {
            taskService.createTask(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_CREATE_SUCCESS);
    }

    @GetMapping("/{sprintId}/{taskId}")
    public BaseResponse<ReadTaskResponse> readTask(@PathVariable Long taskId) {
        ReadTaskResponse response;

        try {
            response = taskService.readTask(taskId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_SUCCESS, response);
    }

    @GetMapping("/{sprintId}/all")
    public BaseResponse<List<ReadAllTaskResponse>> readAllTask(@PathVariable Long sprintId) {

        List<ReadAllTaskResponse> response;

        try {
            response = taskService.readAllTask(sprintId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_SUCCESS, response);
    }

    @GetMapping("/my/all")
    public BaseResponse<List<ReadAllTaskResponse>> readAllMyTask(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

        List<ReadAllTaskResponse> response;

        try {
            response = taskService.readAllMyTask(customUserDetails.getUser());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_SUCCESS, response);
    }

    @PatchMapping("/{sprintId}/status/{taskId}")
    public BaseResponse<BaseResponseStatus> updateTaskStatus(@PathVariable Long taskId, @RequestBody UpdateTaskStatusRequest request) {

        try {
            taskService.updateTaskStatus(taskId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_STATUS_UPDATE_SUCCESS);
    }


}
