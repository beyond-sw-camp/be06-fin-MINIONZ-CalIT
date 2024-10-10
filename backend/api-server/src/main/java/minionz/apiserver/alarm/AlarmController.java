package minionz.apiserver.alarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import minionz.apiserver.alarm.model.request.AlarmRequest;
import minionz.common.alarm.model.response.ReadMyAlarmResponse;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

  private final AlarmService alarmService;

  public AlarmController(AlarmService alarmService) {
    this.alarmService = alarmService;
  }

  // 클라이언트 연결
  @GetMapping("/connect/{receiverId}")
  public SseEmitter connect(@PathVariable Long receiverId) {
    System.out.println("연결 성공");
    return alarmService.addEmitter(receiverId);
  }

  // 특정 클라이언트에게 이벤트 전송
  @PostMapping("/send")
  public void sendEventToClient(@RequestBody AlarmRequest alarmRequest) throws JsonProcessingException {
    alarmService.sendEventsToClients(alarmRequest.getReceiverIds(), alarmRequest.getSenderId(),
        alarmRequest.getAlarmId(), alarmRequest.getType());
  }

  @GetMapping("/my")
  public BaseResponse<List<ReadMyAlarmResponse>> readMyAlarms(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

    List<ReadMyAlarmResponse> response = alarmService.readMyAlarms(customUserDetails.getUser());

    return new BaseResponse<>(BaseResponseStatus.MY_ALARM_READ_SUCCESS, response);
  }
}