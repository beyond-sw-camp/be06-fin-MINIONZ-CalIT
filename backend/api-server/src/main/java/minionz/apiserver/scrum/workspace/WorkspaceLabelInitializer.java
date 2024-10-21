package minionz.apiserver.scrum.workspace;

import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.workspace.model.Workspace;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WorkspaceLabelInitializer {

    public List<SprintLabel> createDefaultSprintLabels(Workspace workspace) {
        return Arrays.asList(
                SprintLabel.builder()
                        .labelName("Feature Development")
                        .description("스프린트 동안 개발할 새로운 기능")
                        .color(1)
                        .workspace(workspace)
                        .build(),

                SprintLabel.builder()
                        .labelName("Bug Fixing")
                        .description("버그 수정 관련 작업")
                        .color(2)
                        .workspace(workspace)
                        .build(),

                SprintLabel.builder()
                        .labelName("Planning")
                        .description("계획 및 스프린트 준비")
                        .color(3)
                        .workspace(workspace)
                        .build(),

                SprintLabel.builder()
                        .labelName("Optimization")
                        .description("성능 최적화 작업")
                        .color(4)
                        .workspace(workspace)
                        .build()
        );
    }

    public List<TaskLabel> createDefaultTaskLabels(Workspace workspace) {
        return Arrays.asList(
                TaskLabel.builder()
                        .labelName("Bug")
                        .description("버그 수정 작업")
                        .color(1)
                        .workspace(workspace)
                        .build(),

                TaskLabel.builder()
                        .labelName("Feature")
                        .description("새로운 기능 개발")
                        .color(2)
                        .workspace(workspace)
                        .build(),

                TaskLabel.builder()
                        .labelName("Chore")
                        .description("기타 유지보수 작업")
                        .color(3)
                        .workspace(workspace)
                        .build(),

                TaskLabel.builder()
                        .labelName("Documentation")
                        .description("문서 작업")
                        .color(4)
                        .workspace(workspace)
                        .build()
        );
    }

    public List<NoteLabel> createDefaultMeetingLabels(Workspace workspace) {
        return Arrays.asList(
                NoteLabel.builder()
                        .labelName("Planning")
                        .description("스프린트 계획 회의")
                        .color(1)
                        .workspace(workspace)
                        .build(),

                NoteLabel.builder()
                        .labelName("Retrospective")
                        .description("스프린트 회고 회의")
                        .color(2)
                        .workspace(workspace)
                        .build(),

                NoteLabel.builder()
                        .labelName("Daily Stand-up")
                        .description("일일 스탠드업 회의")
                        .color(3)
                        .workspace(workspace)
                        .build(),

                NoteLabel.builder()
                        .labelName("Technical Discussion")
                        .description("기술 논의를 위한 회의")
                        .color(4)
                        .workspace(workspace)
                        .build()
        );
    }
}
