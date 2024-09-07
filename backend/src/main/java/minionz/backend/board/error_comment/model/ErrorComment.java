package minionz.backend.board.error_comment.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.board.error_board.model.ErrorBoard;
import minionz.backend.user.model.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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

    private String errCommentContent;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "error_board_id")
    private ErrorBoard errorBoard;

}
