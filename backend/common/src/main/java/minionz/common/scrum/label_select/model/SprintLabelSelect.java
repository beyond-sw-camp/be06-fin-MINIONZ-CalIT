package minionz.common.scrum.label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.sprint.model.Sprint;

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
