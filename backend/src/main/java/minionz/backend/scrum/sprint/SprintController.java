package minionz.backend.scrum.sprint;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.sprint.model.request.CreateSprintRequest;
import minionz.backend.scrum.sprint.model.request.UpdateSprintStatusRequest;
import minionz.backend.scrum.sprint.model.response.ReadAllSprintResponse;
import minionz.backend.scrum.sprint.model.response.ReadSprintResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sprint")
public class SprintController {

  private final SprintService sprintService;

  @PostMapping("/{workspaceId}")
  public BaseResponse<BaseResponseStatus> createSprint(
      @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @RequestBody CreateSprintRequest request) {

    try {
      sprintService.createSprint(customUserDetails.getUser(), request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
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
