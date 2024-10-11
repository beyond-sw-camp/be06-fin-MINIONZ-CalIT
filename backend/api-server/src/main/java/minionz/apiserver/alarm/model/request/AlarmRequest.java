package minionz.apiserver.alarm.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlarmRequest {
    List<Long> receiverIds;
    Long senderId;
    Long alarmId;
    Long type;
}
