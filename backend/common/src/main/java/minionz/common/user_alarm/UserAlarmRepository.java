package minionz.common.user_alarm;

import minionz.common.user.model.User;
import minionz.common.user_alarm.model.UserAlarm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
    List<UserAlarm> findByReceiver(User receiver, Sort sort);
}
