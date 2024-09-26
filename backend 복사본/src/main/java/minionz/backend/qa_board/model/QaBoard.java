package minionz.backend.qa_board.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaBoardId;
}
