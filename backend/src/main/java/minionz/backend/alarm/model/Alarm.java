package minionz.backend.alarm.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.user_alarm.model.UserAlarm;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;
    private String alarmTitle;
    private String alarmContents;

    public static final List<String> PREDEFINED_MESSAGES = List.of(
            "Workspace에 초대되었습니다..",
            "Sprint에 등록되었습니다.",
            "Task가 할당되었습니다.."
    );

    public String setAlarmContents(int messageIndex) {
        if (messageIndex >= 0 && messageIndex < PREDEFINED_MESSAGES.size()) {
            this.alarmContents = PREDEFINED_MESSAGES.get(messageIndex);
        } else {
            throw new IllegalArgumentException("Invalid message index");
        }
        return alarmContents;
    }

    @OneToMany(mappedBy = "alarm", fetch = FetchType.LAZY)
    private List<UserAlarm> userAlarms = new ArrayList<>();
}
