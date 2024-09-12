package minionz.backend.board.error_board.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.board.error_comment.model.ErrorComment;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.user.model.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ErrorBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorBoardId;
    private String errorboardTitle;
    private String errorboardContent;

    @Enumerated(EnumType.STRING)
    private ErrorBoardCategory category;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;


    @OneToMany(mappedBy = "errorBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorComment> commentList= new ArrayList<>();
    @OneToMany(mappedBy = "errorBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorBoardImage> errorBoardImageList= new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workSpace;


}
