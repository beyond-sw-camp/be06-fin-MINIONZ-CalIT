package minionz.backend.user_alarm;

import minionz.backend.user.model.User;
import minionz.backend.user_alarm.model.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
    List<UserAlarm> findByReceiver(Optional<User> receiver);
}
