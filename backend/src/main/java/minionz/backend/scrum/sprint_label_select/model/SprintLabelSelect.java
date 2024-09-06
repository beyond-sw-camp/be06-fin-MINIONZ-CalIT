package minionz.backend.scrum.sprint_label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.label.model.SprintLabel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SprintLabelSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintLabelSelectId;

    // SprintLabelSelect : SprintLabel = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_label_id")
    private SprintLabel sprintLabel;

    // SprintLabelSelect : Sprint = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
