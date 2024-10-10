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
                .alarmStatus(0) // 전송됨
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
        }
    }


    public SseEmitter addEmitter(Long receiverId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(String.valueOf(receiverId), emitter);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> alarmData = new HashMap<>();
        alarmData.put("AlarmTitle", "Calit");
        alarmData.put("AlarmContents", "접속을 환영합니다.");
        alarmData.put("idx", String.valueOf(receiverId));

        String jsonData = objectMapper.writeValueAsString(alarmData);

        try {
            emitter.send(jsonData); // 클라이언트에게 연결 성공 메시지 전송
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리 (필요시)
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

    public List<ReadMyAlarmResponse> readMyAlarms(User user) {
        List<UserAlarm> alarms = userAlarmRepository.findByReceiver(user, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<ReadMyAlarmResponse> response = alarms.stream().filter(
                        userAlarm -> userAlarm.getAlarmStatus() != 1)
                .map(userAlarm -> ReadMyAlarmResponse
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

    public void updateAlarmStatus(Long userAlarmId) {
        UserAlarm alarm = userAlarmRepository.findByUserAlarmId(userAlarmId);
        alarm.setAlarmStatus(1);
        userAlarmRepository.save(alarm);
    }

    // 상태가 0인 알람만 반환하는 메서드
    public List<ReadMyAlarmResponse> getAlarmsByStatus() {
        List<UserAlarm> alarms = userAlarmRepository.findByAlarmStatus(0);
        List<ReadMyAlarmResponse> response = alarms.stream().map(
                userAlarm -> ReadMyAlarmResponse
                        .builder()
                        .idx(userAlarm.getType())
                        .type(userAlarm.getAlarm().getAlarmId())
                        .title(userAlarm.getAlarm().getAlarmTitle())
                        .content(userAlarm.getAlarm().getAlarmContents())
                        .time(userAlarm.getCreatedAt())
                        .status(userAlarm.getAlarmStatus())
                        .build()
        ).toList();
        return response;
    }
}