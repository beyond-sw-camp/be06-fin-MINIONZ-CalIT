package minionz.backend.scrum.label;

import minionz.backend.scrum.label.model.SprintLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintLabelRepository extends JpaRepository<SprintLabel, Long> {
    SprintLabel findByLabelName(String labelName);
}
