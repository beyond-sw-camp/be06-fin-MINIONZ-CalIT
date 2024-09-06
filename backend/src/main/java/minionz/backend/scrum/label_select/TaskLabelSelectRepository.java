package minionz.backend.scrum.label_select;

import minionz.backend.scrum.label_select.model.TaskLabelSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLabelSelectRepository extends JpaRepository<TaskLabelSelect, Long> {
}
