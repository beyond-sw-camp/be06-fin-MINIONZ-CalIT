package minionz.apiserver.scrum.label_select;

import minionz.common.scrum.label_select.model.TaskLabelSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLabelSelectRepository extends JpaRepository<TaskLabelSelect, Long> {
}
