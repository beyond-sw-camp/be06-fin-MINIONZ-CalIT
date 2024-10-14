package minionz.common.board.error_comment.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.user.model.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
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
