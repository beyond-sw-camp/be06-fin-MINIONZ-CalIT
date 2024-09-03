package minionz.backend.sprint.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintId;
}
