package minionz.common.user.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.error_comment.model.ErrorComment;
import minionz.common.board.qa_board.model.QaBoard;
import minionz.common.board.qa_comment.model.QaComment;
import minionz.common.chat.chat_bot.model.ChatBot;
import minionz.common.chat.chat_participation.model.ChatParticipation;
import minionz.common.scrum.issue.model.Issue;
import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
import minionz.common.scrum.sprint_participation.model.SprintParticipation;
import minionz.common.scrum.task_participation.model.TaskParticipation;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.user_alarm.model.UserAlarm;
import org.hibernate.annotations.BatchSize;

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

    // ChatBot 1 : 1
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private ChatBot chatBot;

    // ChatParticipation 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipations = new ArrayList<>();

    // User : WorkspaceParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<WorkspaceParticipation> workspaceParticipations = new ArrayList<>();

    // User : SprintParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<SprintParticipation> sprintParticipations = new ArrayList<>();

    // User : TaskParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TaskParticipation> taskParticipations = new ArrayList<>();

    // User : MeetingParticipation = 1 : N
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();

    // User : errorBoardList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorBoard> errorBoardList = new ArrayList<>();

    // User : errorCommentList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ErrorComment> errorCommentList = new ArrayList<>();

    // User : qaBoardList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QaBoard> qaBoardList = new ArrayList<>();

    // User : qaCommentList = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QaComment> qaCommentList = new ArrayList<>();

    // User : Issue = 1 : N
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<UserAlarm> receiverAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<UserAlarm> senderAlarms = new ArrayList<>();
}
