package minionz.backend.alarm;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minionz.backend.alarm.model.Alarm;
import minionz.backend.alarm.model.response.ReadMyAlarmResponse;
import minionz.backend.user.UserRepository;
import minionz.backend.user.model.User;
import minionz.backend.user_alarm.UserAlarmRepository;
import minionz.backend.user_alarm.model.UserAlarm;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class AlarmService {

    private final Map<Long, Boolean> emitterStatus = new ConcurrentHashMap<>();
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

    public void sendEventsToClients(List<Long> receiverIds, Long senderId, Long alarmId, Long type) throws JsonProcessingException {
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
            sendAlarmToReceiver(receiver, sender.get(), sendAlarm.get(), type);
        }
    }

    public void sendScheduledEventsToClients(List<Long> receiverIds, Long alarmId, Long type) {
        Optional<Alarm> sendAlarm = alarmRepository.findById(alarmId);

        if (!sendAlarm.isPresent()) {
            return; // 알람이 존재하지 않으면 종료
        }
        List<User> receivers = new ArrayList<>();

        // 유저 리스트 초기화 및 존재하는 유저 추가
        for (Long receiverId : receiverIds) {
            Optional<User> receiver = userRepository.findById(receiverId);
            receiver.ifPresent(receivers::add);
        }

        // 각 수신자에게 알림 전송
        for (User receiver : receivers) {
            sendScheduledAlarmToReceiver(receiver, sendAlarm.get(), type);
        }
    }

    private void sendAlarmToReceiver(User receiver, User sender, Alarm sendAlarm, Long type) throws JsonProcessingException {
        SseEmitter emitter = emitters.get(String.valueOf(receiver.getUserId()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> alarmData = new HashMap<>();
        alarmData.put("AlarmTitle", sendAlarm.getAlarmTitle());
        alarmData.put("AlarmContents", sendAlarm.getAlarmContents());
        alarmData.put("idx", String.valueOf(type));

        String jsonData = objectMapper.writeValueAsString(alarmData);

        // 알림 객체 생성 및 저장
        UserAlarm userAlarm = UserAlarm.builder()
                .receiver(receiver)
                .sender(sender)
                .alarm(sendAlarm)
                .alarmStatus(1) // 전송됨
                .type(type)
                .build();
        userAlarmRepository.save(userAlarm);

        // 연결된 경우
        if (emitter != null && emitterStatus.getOrDefault(receiver.getUserId(), false)) {
            try {
                emitter.send(SseEmitter.event().name("message").data(jsonData));
                System.out.println("알람 전송 완료");
            } catch (IOException e) {
                emitters.remove(receiver.getUserId()); // 오류 시 emitter 제거
                emitterStatus.remove(receiver.getUserId()); // 상태도 제거
            }
        } else {
            // 연결되지 않은 경우, 알림 저장
            pendingAlarms.computeIfAbsent(receiver.getUserId(), k -> new ArrayList<>()).add(sendAlarm);
        }
    }

    private void sendScheduledAlarmToReceiver(User receiver, Alarm sendAlarm, Long type) {
        SseEmitter emitter = emitters.get(String.valueOf(receiver.getUserId()));

        // 알림 객체 생성 및 저장
        UserAlarm userAlarm = UserAlarm.builder()
                .receiver(receiver)
                .sender(null)
                .alarm(sendAlarm)
                .alarmStatus(1) // 전송됨
                .type(type)
                .build();
        userAlarmRepository.save(userAlarm);

        // 연결된 경우
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data("AlarmContents : " + sendAlarm.getAlarmContents() + ",type : " + type));
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
        emitterStatus.put(receiverId, true); // 상태를 true로 초기화


        // 기존 보관된 알림 확인
        List<Alarm> alarms = pendingAlarms.get(receiverId);
        if (alarms != null) {
            alarms.forEach(alarm -> {
                try {
                    String message = alarmRepository.findById(alarm.getAlarmId()).get().getAlarmContents();
                    emitter.send(SseEmitter.event().name("message").data(message));
                } catch (IOException e) {
                    emitters.remove(receiverId);
                    emitterStatus.remove(receiverId); // 상태 제거
                }
            });
            pendingAlarms.remove(receiverId); // 보낸 후 보관 목록에서 제거
        }

        emitter.onCompletion(() -> {
            emitters.remove(receiverId);
            emitterStatus.remove(receiverId); // 상태 제거
        });
        emitter.onTimeout(() -> {
            emitters.remove(receiverId);
            emitterStatus.remove(receiverId); // 상태 제거
        });
        return emitter;
    }

    public List<ReadMyAlarmResponse> readMyAlarms(User user){
        List<UserAlarm> alarms = userAlarmRepository.findByReceiver(user, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<ReadMyAlarmResponse> response =  alarms.stream().map(
               userAlarm -> ReadMyAlarmResponse
                       .builder()
                       .idx(userAlarm.getType())
                       .type(userAlarm.getAlarm().getAlarmId())
                       .title(userAlarm.getAlarm().getAlarmTitle())
                       .content(userAlarm.getAlarm().getAlarmContents())
                       .time(userAlarm.getCreatedAt())
                       .build()
       ).toList();

        return response;
    }
}