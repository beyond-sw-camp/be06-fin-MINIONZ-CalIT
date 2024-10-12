package minionz.common.scrum.sprint_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SprintParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintParticipationId;

    private Boolean isManager;

    // SprintParticipation : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // SprintParticipation : Sprint = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
