package minionz.backend.user.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.board.error_board.model.ErrorBoard;
import minionz.backend.board.error_comment.model.ErrorComment;
import minionz.backend.chat.chat_participation.model.ChatParticipation;
import minionz.backend.scrum.issue.model.Issue;
import minionz.backend.scrum.task_participation.model.TaskParticipation;

import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.backend.user_alarm.model.UserAlarm;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String loginId;
    private String password;
    @Column(unique = true)
    private String email;
    private String userName;
    private Integer loginType;
    private boolean isEnabled = false;
    private String createdAt;
    private String role = "ROLE_ADMIN";
    private String provider; // google
    private String providerId; // google 유저 고유 ID
    private Integer persona;

    // ChatParticipation 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipations = new ArrayList<>();

    // User : WorkspaceParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<WorkspaceParticipation> workspaceParticipations = new ArrayList<>();

    // User : SprintParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SprintParticipation> sprintParticipations = new ArrayList<>();

    // User : TaskParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TaskParticipation> taskParticipations = new ArrayList<>();

    // User : MeetingParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();

    // User : errorBoardList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorBoard> errorBoardList = new ArrayList<>();

    // User : errorCommentList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorComment> errorCommentList = new ArrayList<>();

    // User : Issue = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<UserAlarm> receiverAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<UserAlarm> senderAlarms = new ArrayList<>();
}
