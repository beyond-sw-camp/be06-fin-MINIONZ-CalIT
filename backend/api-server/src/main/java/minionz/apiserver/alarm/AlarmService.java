package minionz.apiserver.alarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minionz.common.alarm.AlarmRepository;
import minionz.common.alarm.model.Alarm;
import minionz.common.alarm.model.response.ReadMyAlarmResponse;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import minionz.common.user_alarm.UserAlarmRepository;
import minionz.common.user_alarm.model.UserAlarm;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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

    public void sendScheduledEventsToClients(List<Long> receiverIds, Long alarmId, Long type) throws JsonProcessingException {
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
                .alarmStatus(0L) // 전송됨
                .type(type)
                .build();
        userAlarmRepository.save(userAlarm);

        // 연결된 경우
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(jsonData));
            } catch (IOException e) {
                emitters.remove(receiver.getUserId()); // 오류 시 emitter 제거
            }
        }
    }

    private void sendScheduledAlarmToReceiver(User receiver, Alarm sendAlarm, Long type) throws JsonProcessingException {
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
                .sender(null)
                .alarm(sendAlarm)
                .alarmStatus(0L) // 읽음 안읽음
                .type(type)
                .build();
        userAlarmRepository.save(userAlarm);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(jsonData));
            } catch (IOException e) {
                emitters.remove(receiver.getUserId()); // 오류 시 emitter 제거
            }
        }
    }


    public SseEmitter addEmitter(Long receiverId) throws JsonProcessingException {
        SseEmitter emitter = new SseEmitter(0L);
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

        emitter.onCompletion(() -> emitters.remove(receiverId));
        emitter.onTimeout(() -> emitters.remove(receiverId));
        return emitter;
    }

    public List<ReadMyAlarmResponse> readMyAlarms(User user){
        List<UserAlarm> alarms = userAlarmRepository.findByReceiver(user, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<ReadMyAlarmResponse> response =  alarms.stream().filter(
                        userAlarm -> userAlarm.getAlarmStatus() != 1)
                .map(userAlarm -> ReadMyAlarmResponse
                        .builder()
                        .idx(userAlarm.getType())
                        .type(userAlarm.getAlarm().getAlarmId())
                        .title(userAlarm.getAlarm().getAlarmTitle())
                        .content(userAlarm.getAlarm().getAlarmContents())
                        .time(userAlarm.getCreatedAt())
                        .status(userAlarm.getAlarmStatus())
                        .userAlarmId(userAlarm.getUserAlarmId())
                        .build()
                ).toList();

        return response;
    }

    public void updateAlarmStatus(Long userAlarmId) {
        UserAlarm alarm = userAlarmRepository.findByUserAlarmId(userAlarmId);
        alarm.setAlarmStatus(1L);
        userAlarmRepository.save(alarm);
    }

    // 상태가 0인 알람만 반환하는 메서드
    public List<ReadMyAlarmResponse> getAlarmsByStatus() {
        List<UserAlarm> alarms = userAlarmRepository.findByAlarmStatus(0);
        List<ReadMyAlarmResponse> response =  alarms.stream().map(
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