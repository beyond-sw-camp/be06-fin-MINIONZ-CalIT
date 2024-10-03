package minionz.backend.user_alarm;
import org.springframework.data.domain.Sort;

import minionz.backend.user.model.User;
import minionz.backend.user_alarm.model.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
    List<UserAlarm> findByReceiver(User receiver, Sort sort);
}
