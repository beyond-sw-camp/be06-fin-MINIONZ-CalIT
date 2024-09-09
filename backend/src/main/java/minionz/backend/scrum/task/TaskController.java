package minionz.backend.scrum.task;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.task.model.request.CreateTaskRequest;
import minionz.backend.user.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> craeteTask(@RequestBody CreateTaskRequest request){
        User user = User.builder().userId(1L).build();

        try{
            taskService.createTask(user,request);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_CREATE_SUCCESS);
    }


}
