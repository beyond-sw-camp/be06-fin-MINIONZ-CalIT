package minionz.backend.alarm;

import minionz.backend.alarm.model.request.AlarmRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

        return alarmService.addEmitter(receiverId);
    }

    // 특정 클라이언트에게 이벤트 전송
    @PostMapping("/send")
    public void sendEventToClient(@RequestBody AlarmRequest alarmRequest) {
        alarmService.sendEventsToClients(alarmRequest.getReceiverIds(), alarmRequest.getSenderId(), alarmRequest.getAlarmId());
    }
}