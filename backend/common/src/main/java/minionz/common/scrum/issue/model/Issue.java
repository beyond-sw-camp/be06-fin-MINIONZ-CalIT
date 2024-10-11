package minionz.common.scrum.issue.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.model.User;
import minionz.common.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Issue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    private String issueTitle;
    private String issueContents;

    private boolean status;

    // 시작일과 종료일 추가
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Issue : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // Issue : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User manager;
}
