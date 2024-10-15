package minionz.apiserver.scrum.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.apiserver.scrum.schedule.request.RemindAlarmRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


import java.io.IOException;


@Service
@EnableScheduling
@RequiredArgsConstructor
public class ReminderService {

    private final AlarmService alarmService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "reminder-alarm")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        try {
            RemindAlarmRequest request = objectMapper.readValue(record.value(), RemindAlarmRequest.class);
            alarmService.sendScheduledEventsToClients(request.getParticipants(), request.getAlarmId(), request.getId() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
