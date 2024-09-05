package minionz.backend.scrum.task_label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.task_label.model.TaskLabel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskLabelSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskLabelSelectId;

    // TaskLabelSelect : TaskLabel = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_label_id")
    private TaskLabel taskLabel;

    // TaskLabelSelect : Task = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
