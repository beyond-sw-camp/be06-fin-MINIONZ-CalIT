package minionz.backend.scrum.task;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.task.model.request.CreateTaskRequest;
import minionz.backend.scrum.task.model.response.ReadAllTaskResponse;
import minionz.backend.scrum.task.model.response.ReadTaskResponse;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> craeteTask(@RequestBody CreateTaskRequest request) {
        User user = User.builder().userId(1L).build();

        try {
            taskService.createTask(user, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_CREATE_SUCCESS);
    }

    @GetMapping("/{taskId}")
    public BaseResponse<ReadTaskResponse> readTask(@PathVariable Long taskId) {
        User user = User.builder().userId(1L).build();
        ReadTaskResponse response;

        try {
            response = taskService.readTask(taskId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_SUCCESS, response);
    }

    @GetMapping("/all/{sprintId}")
    public BaseResponse<List<ReadAllTaskResponse>> readAllTask(@PathVariable Long sprintId) {
        User user = User.builder().userId(1L).build();
        List<ReadAllTaskResponse> response;

        try {
            response = taskService.readAllTask(sprintId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_READ_ALL_SUCCESS, response);
    }


}
