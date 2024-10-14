package minionz.common.alarm.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.user_alarm.model.UserAlarm;

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

    @OneToMany(mappedBy = "alarm", fetch = FetchType.LAZY)
    private List<UserAlarm> userAlarms = new ArrayList<>();
}
