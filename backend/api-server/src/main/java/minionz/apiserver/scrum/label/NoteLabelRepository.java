package minionz.apiserver.scrum.label;

import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteLabelRepository extends JpaRepository<NoteLabel, Long> {
    NoteLabel findByLabelName(String labelName);
    List<NoteLabel> findByWorkspaceWorkspaceId(Long workspaceId);
    NoteLabel findByLabelNameAndWorkspace(String labelName, Workspace workspace);
}
