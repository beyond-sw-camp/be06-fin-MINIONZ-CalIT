package minionz.backend.alarm;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final UserAlarmRepository userAlarmRepository;

    public AlarmService(UserRepository userRepository, AlarmRepository alarmRepository, UserAlarmRepository userAlarmRepository) {
        this.userRepository = userRepository;
        this.alarmRepository = alarmRepository;
        this.userAlarmRepository = userAlarmRepository;
    }

    public SseEmitter addEmitter(Long receiverId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        Optional<User> receiver = userRepository.findById(receiverId);
        emitters.put(String.valueOf(receiver.get().getUserId()), emitter);
        try {
            emitter.send(SseEmitter.event().name("message").data("EventStream Created. [clientId=" + String.valueOf(receiver.get().getUserId()) + "]"));
            List<UserAlarm> useralarms = userAlarmRepository.findByReceiver(receiver);
            useralarms.forEach(userAlarm -> {
                try {
                    emitter.send(SseEmitter.event().name("message").data(userAlarm.getAlarm().getAlarmContents()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        emitter.onCompletion(() -> emitters.remove(String.valueOf(receiver.get().getUserId())));
        emitter.onTimeout(() -> emitters.remove(String.valueOf(receiver.get().getUserId())));

        return emitter;
    }

    public void sendEventsToClient(Long receiverId, Long senderId, Long alarm) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        Optional<Alarm> sendAlarm = alarmRepository.findById(alarm);
        SseEmitter emitter = emitters.get("" + receiver.get().getUserId());
        if (emitter != null) {
            try {
                List<UserAlarm> useralarms = userAlarmRepository.findByReceiver(receiver);
                useralarms.forEach(userAlarm -> {
                    try {
                        emitter.send(SseEmitter.event().name("message").data(userAlarm.getAlarm().getAlarmContents()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                    UserAlarm userAlarm = UserAlarm.builder()
                        .receiver(receiver.get())
                        .sender(sender.get())
                        .alarm(sendAlarm.get())
                        .alarmStatus(1) //0 : 전송 안됨, 1 : 전송됐지만 읽지 않음, 2 : 전송됐고 읽음
                        .build();
                userAlarmRepository.save(userAlarm);
                emitter.send(SseEmitter.event().name("message").data(sendAlarm.get().getAlarmContents()));
            } catch (IOException e) {
                // 예외 처리 (연결 문제가 발생한 경우)
                emitters.remove(receiver.get().getUserId());
            }
        } else {
            try {
                UserAlarm userAlarm = UserAlarm.builder()
                        .receiver(receiver.get())
                        .sender(sender.get())
                        .alarm(sendAlarm.get())
                        .alarmStatus(0)
                        .build();
                userAlarmRepository.save(userAlarm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
