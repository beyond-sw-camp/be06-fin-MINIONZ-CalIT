package minionz.backend.alarm;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import minionz.backend.alarm.model.Alarm;
import minionz.backend.user.UserRepository;
import minionz.backend.user.model.User;
import minionz.backend.user_alarm.UserAlarmRepository;
import minionz.backend.user_alarm.model.UserAlarm;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

@Service
public class AlarmService {

    private final Map<Long, List<Alarm>> pendingAlarms = new HashMap<>();
    private final Map<String, SseEmitter> emitters = new HashMap<>();
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final UserAlarmRepository userAlarmRepository;

    public AlarmService(UserRepository userRepository, AlarmRepository alarmRepository, UserAlarmRepository userAlarmRepository) {
        this.userRepository = userRepository;
        this.alarmRepository = alarmRepository;
        this.userAlarmRepository = userAlarmRepository;
    }
    public void sendEventsToClient(Long receiverId, Long senderId, Long alarmId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        Optional<Alarm> sendAlarm = alarmRepository.findById(alarmId);

        if (sender.isPresent() && receiver.isPresent() && sendAlarm.isPresent()) {
            SseEmitter emitter = emitters.get("" + receiver.get().getUserId());

            // 알림 객체 생성
            UserAlarm userAlarm = UserAlarm.builder()
                    .receiver(receiver.get())
                    .sender(sender.get())
                    .alarm(sendAlarm.get())
                    .alarmStatus(1) // 전송됨
                    .build();
            userAlarmRepository.save(userAlarm);

            // 연결된 경우
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("message").data(sendAlarm.get().getAlarmContents()));
                } catch (IOException e) {
                    emitters.remove(receiver.get().getUserId());
                }
            } else {
                // 연결되지 않은 경우, 알림 저장
                pendingAlarms.computeIfAbsent(receiver.get().getUserId(), k -> new ArrayList<>()).add(sendAlarm.get());
            }
        }
    }

    public SseEmitter addEmitter(Long receiverId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(String.valueOf(receiverId), emitter);

        // 기존 보관된 알림 확인
        List<Alarm> alarms = pendingAlarms.get(receiverId);
        if (alarms != null) {
            alarms.forEach(alarm -> {
                try {
                    emitter.send(SseEmitter.event().name("message").data(alarm.getAlarmContents()));
                } catch (IOException e) {
                    emitters.remove(receiverId);
                }
            });
            pendingAlarms.remove(receiverId); // 보낸 후 보관 목록에서 제거
        }

        emitter.onCompletion(() -> emitters.remove(receiverId));
        emitter.onTimeout(() -> emitters.remove(receiverId));
        return emitter;
    }
}
