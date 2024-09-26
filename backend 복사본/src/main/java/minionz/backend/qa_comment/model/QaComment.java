package minionz.backend.qa_comment.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class QaComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaCommentId;
}
