package minionz.apiserver.scrum.label_select;

import minionz.common.scrum.label_select.model.SprintLabelSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintLabelSelectRepository extends JpaRepository<SprintLabelSelect, Long> {
}
