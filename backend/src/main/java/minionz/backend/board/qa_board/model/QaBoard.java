package minionz.backend.board.qa_board.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
