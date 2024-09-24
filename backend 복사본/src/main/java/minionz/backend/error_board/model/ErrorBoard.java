package minionz.backend.error_board.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ErrorBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorBoardId;
}
