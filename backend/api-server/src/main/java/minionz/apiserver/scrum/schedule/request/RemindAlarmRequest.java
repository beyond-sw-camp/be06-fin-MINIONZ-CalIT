package minionz.apiserver.scrum.schedule.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RemindAlarmRequest {
    List<Long> participants;
    Long id;
    Long alarmId;
}
