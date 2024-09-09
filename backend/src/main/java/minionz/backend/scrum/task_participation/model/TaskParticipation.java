package minionz.backend.scrum.task_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskParticipationId;

    // TaskParticipation : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // TaskParticipation : Task = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
