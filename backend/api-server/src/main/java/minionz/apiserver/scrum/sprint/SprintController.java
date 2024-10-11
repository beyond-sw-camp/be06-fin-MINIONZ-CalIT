package minionz.apiserver.scrum.sprint;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.sprint.model.request.CreateSprintRequest;
import minionz.apiserver.scrum.sprint.model.request.UpdateSprintStatusRequest;
import minionz.apiserver.scrum.sprint.model.response.ReadAllSprintResponse;
import minionz.apiserver.scrum.sprint.model.response.ReadSprintResponse;
import minionz.apiserver.user.UserService;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sprint")
public class SprintController {

  private final SprintService sprintService;
  private final UserService userService;

  @PostMapping("/{workspaceId}")
  public BaseResponse<BaseResponseStatus> createSprint(
          @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateSprintRequest request, HttpServletResponse response) {

    try {
      sprintService.createSprint(customUserDetails.getUser(), request);
      userService.sendNewToken(customUserDetails, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
    }

      return new BaseResponse<>(BaseResponseStatus.SPRINT_CREATE_SUCCESS);
  }

  @GetMapping("/{workspaceId}/{sprintId}")
  public BaseResponse<ReadSprintResponse> readSprint(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long sprintId) {
    ReadSprintResponse response;

    try {
      response = sprintService.readSprint(customUserDetails.getUser(), sprintId);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.SPRINT_READ_SUCCESS, response);
  }

  @GetMapping("/all/{workspaceId}")
  public BaseResponse<List<ReadAllSprintResponse>> readAllSprint(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long workspaceId) {

    List<ReadAllSprintResponse> response;

    try {
      response = sprintService.readAllSprint(customUserDetails.getUser(), workspaceId);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.SPRINT_READ_ALL_SUCCESS, response);
  }

  @PatchMapping("/{sprintId}/status")
  public BaseResponse<BaseResponseStatus> updateSprintStatus(@PathVariable Long sprintId,
      @RequestBody UpdateSprintStatusRequest request) {

    try {
      sprintService.updateSprintStatus(sprintId, request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.SPRINT_STATUS_UPDATE_SUCCESS);
  }

}
