package minionz.common.alarm;

import minionz.common.alarm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
