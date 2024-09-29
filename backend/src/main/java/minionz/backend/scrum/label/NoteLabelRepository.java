package minionz.backend.scrum.label;

import minionz.backend.scrum.label.model.NoteLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteLabelRepository extends JpaRepository<NoteLabel, Long> {
    NoteLabel findByLabelName(String labelName);
    List<NoteLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
