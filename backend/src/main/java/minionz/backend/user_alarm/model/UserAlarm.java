package minionz.backend.user_alarm.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.alarm.model.Alarm;
import minionz.backend.user.model.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAlarmId;

    private Integer alarmStatus;
    private String category;

    // UserAlarm : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    // UserAlarm : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    // UserAlarm : Alarm = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    private Long type;
}
