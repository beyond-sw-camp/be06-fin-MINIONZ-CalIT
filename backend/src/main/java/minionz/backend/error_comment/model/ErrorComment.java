package minionz.backend.error_comment.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ErrorComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorCommentId;
}
