package minionz.common.board.qa_comment.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.board.qa_board.model.QaBoard;
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
public class QaComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaCommentId;

    private String qaCommentTitle;
    private String qaCommentContent;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "qa_board_id")
    private QaBoard qaBoard;

}
