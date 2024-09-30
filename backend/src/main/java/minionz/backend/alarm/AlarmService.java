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
import java.util.stream.Collectors;

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

    public void sendEventsToClients(List<Long> receiverIds, Long senderId, Long alarmId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<Alarm> sendAlarm = alarmRepository.findById(alarmId);

        if (!sender.isPresent() || !sendAlarm.isPresent()) {
            return; // 송신자 또는 알람이 존재하지 않으면 종료
        }
        List<User> receivers = new ArrayList<>();

        // 유저 리스트 초기화 및 존재하는 유저 추가
        for (Long receiverId : receiverIds) {
            Optional<User> receiver = userRepository.findById(receiverId);
            receiver.ifPresent(receivers::add);
        }

        // 각 수신자에게 알림 전송
        for (User receiver : receivers) {
            sendAlarmToReceiver(receiver, sender.get(), sendAlarm.get());
        }
    }

    private void sendAlarmToReceiver(User receiver, User sender, Alarm sendAlarm) {
        SseEmitter emitter = emitters.get(String.valueOf(receiver.getUserId()));

        // 알림 객체 생성 및 저장
        UserAlarm userAlarm = UserAlarm.builder()
                .receiver(receiver)
                .sender(sender)
                .alarm(sendAlarm)
                .alarmStatus(1) // 전송됨
                .build();
        userAlarmRepository.save(userAlarm);

        // 연결된 경우
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(sendAlarm.getAlarmContents()));
            } catch (IOException e) {
                emitters.remove(receiver.getUserId()); // 오류 시 emitter 제거
            }
        } else {
            // 연결되지 않은 경우, 알림 저장
            pendingAlarms.computeIfAbsent(receiver.getUserId(), k -> new ArrayList<>()).add(sendAlarm);
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
                    String message = alarmRepository.findById(alarm.getAlarmId()).get().getAlarmContents();
                    emitter.send(SseEmitter.event().name("message").data(message));
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