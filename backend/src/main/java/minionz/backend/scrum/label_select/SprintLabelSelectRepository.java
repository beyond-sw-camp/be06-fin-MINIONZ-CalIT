package minionz.backend.scrum.label_select;

import minionz.backend.scrum.label_select.model.SprintLabelSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintLabelSelectRepository extends JpaRepository<SprintLabelSelect, Long> {
}
