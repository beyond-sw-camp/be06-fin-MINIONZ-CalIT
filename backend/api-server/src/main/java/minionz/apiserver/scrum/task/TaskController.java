package minionz.apiserver.scrum.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.task.model.request.CreateTaskRequest;
import minionz.apiserver.scrum.task.model.request.UpdateTaskStatusRequest;
import minionz.apiserver.scrum.task.model.response.ReadAllTaskResponse;
import minionz.apiserver.scrum.task.model.response.ReadTaskResponse;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.common.scrum.task.model.TaskStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/{sprintId}")
    public BaseResponse<BaseResponseStatus> createTask(@PathVariable Long sprintId,
                                                       @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateTaskRequest request) {

        try {
            taskService.createTask(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_CREATE_SUCCESS);
    }

    @GetMapping("/{workspaceId}/{taskId}")
    public BaseResponse<ReadTaskResponse> readTask(@PathVariable Long taskId, @PathVariable Long workspaceId) {
        ReadTaskResponse response;

        try {
            response = taskService.readTask(taskId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_SUCCESS, response);
    }

    @GetMapping("/{workspaceId}/{sprintId}/all/status")
    public BaseResponse<List<Map<TaskStatus, List<ReadAllTaskResponse>>>> readAllTaskByStatus(@PathVariable Long workspaceId,@PathVariable Long sprintId) {

        List<Map<TaskStatus, List<ReadAllTaskResponse>>> response;

        try {
            response = taskService.readAllTaskByStatus(sprintId, null);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_BY_STATUS_SUCCESS, response);
    }

    @GetMapping("/{workspaceId}/{sprintId}/all/status/{userId}")
    public BaseResponse<List<Map<TaskStatus, List<ReadAllTaskResponse>>>> readAllTaskByStatusAndUser(@PathVariable Long workspaceId,@PathVariable Long sprintId, @PathVariable Long userId) {

        List<Map<TaskStatus, List<ReadAllTaskResponse>>> response;

        try {
            response = taskService.readAllTaskByStatus(sprintId, userId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_BY_STATUS_SUCCESS, response);
    }

    @GetMapping("/{workspaceId}/{sprintId}/all")
    public BaseResponse<List<ReadAllTaskResponse>> readAllTask(@PathVariable Long workspaceId,@PathVariable Long sprintId) {

        List<ReadAllTaskResponse> response;

        try {
            response = taskService.readAllTask(sprintId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_SUCCESS, response);
    }

    @GetMapping("/my/all")
    public BaseResponse< List<Map<TaskStatus, List<ReadAllTaskResponse>>>> readAllMyTask(
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

        List<Map<TaskStatus, List<ReadAllTaskResponse>>> response;

        try {
            response = taskService.readAllMyTask(customUserDetails.getUser());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_TASK_READ_SUCCESS, response);
    }

    @PatchMapping("/{sprintId}/status/{taskId}")
    public BaseResponse<BaseResponseStatus> updateTaskStatus(@PathVariable Long taskId, @PathVariable Long sprintId,
                                                             @RequestBody UpdateTaskStatusRequest request) {

        try {
            taskService.updateTaskStatus(taskId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_STATUS_UPDATE_SUCCESS);
    }

    @PatchMapping("/my/status/{taskId}")
    public BaseResponse<BaseResponseStatus> updateMyTaskStatus(@PathVariable Long taskId,
                                                             @RequestBody UpdateTaskStatusRequest request) {

        try {
            taskService.updateTaskStatus(taskId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_TASK_CHANGE_STATUS_SUCCESS);
    }

}
