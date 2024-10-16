package minionz.apiserver.scrum.schedule;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.scrum.schedule.request.ReadScheduleRequest;
import minionz.apiserver.scrum.schedule.response.*;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.apiserver.scrum.sprint.model.response.Participant;
import minionz.common.scrum.issue.IssueRepository;
import minionz.common.scrum.issue.model.Issue;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.SprintRepository;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.task.TaskRepository;
import minionz.common.scrum.task.model.Task;
import minionz.common.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final SprintRepository sprintRepository;
    private final MeetingRepository meetingRepository;
    private final TaskRepository taskRepository;
    private final IssueRepository issueRepository;

    public ReadMonthlyResponse readWorkspaceMonthly(Long workspaceId, ReadScheduleRequest request) {
        List<Sprint> sprints = sprintRepository.findSprintsInPeriod(workspaceId, request.getStartDate(), request.getEndDate());
        List<Meeting> meetings = meetingRepository.findMeetingsInPeriod(workspaceId, request.getStartDate(), request.getEndDate());

        return makeMonthlyResponse(sprints, meetings);
    }

    public ReadMonthlyResponse readMyspaceMonthly(User user, ReadScheduleRequest request) {
        List<Sprint> sprints = sprintRepository.findMySprintsInPeriod(user.getUserId(), request.getStartDate(), request.getEndDate());
        List<Meeting> meetings = meetingRepository.findMyMeetingsInPeriod(user.getUserId(), request.getStartDate(), request.getEndDate());

        return makeMonthlyResponse(sprints, meetings);
    }


    public ReadWeeklyResponse readWorkspaceWeekly(Long workspaceId, ReadScheduleRequest request) {
        Pageable pageable = PageRequest.of(0, 3);

        List<Sprint> sprints = sprintRepository.findSprintsInPeriod(workspaceId, request.getStartDate(), request.getEndDate());
        List<Meeting> meetings = meetingRepository.findMeetingsInPeriod(workspaceId, request.getStartDate(), request.getEndDate());
        List<Task> tasks = taskRepository.findUpcomingWorkspaceTasks(workspaceId, request.getStartDate(), request.getEndDate(), pageable);
        List<Issue> issues = issueRepository.findUpcomingWorkspaceIssues(workspaceId, pageable);

        return makeWeeklyResponse(sprints, meetings, tasks, issues);
    }

    public ReadWeeklyResponse readMyspaceWeekly(User user, ReadScheduleRequest request) {
        Pageable pageable = PageRequest.of(0, 3);

        List<Sprint> sprints = sprintRepository.findMySprintsInPeriod(user.getUserId(), request.getStartDate(), request.getEndDate());
        List<Meeting> meetings = meetingRepository.findMyMeetingsInPeriod(user.getUserId(), request.getStartDate(), request.getEndDate());
        List<Task> tasks = taskRepository.findUpcomingMyTasks(user.getUserId(), request.getStartDate(), request.getEndDate(), pageable);
        List<Issue> issues = issueRepository.findUpcomingMyIssues(user.getUserId(), pageable);

        return makeWeeklyResponse(sprints, meetings, tasks, issues);
    }

    public ReadMonthlyResponse makeMonthlyResponse(List<Sprint> sprints, List<Meeting> meetings) {
        return ReadMonthlyResponse
                .builder()
                .sprints(sprints.stream().map(
                        sprint -> SprintResponse
                                .builder()
                                .id(sprint.getSprintId())
                                .title(sprint.getSprintTitle())
                                .startDate(sprint.getStartDate())
                                .endDate(sprint.getEndDate())
                                .build()).toList())
                .meetings(meetings.stream().map(
                        meeting -> MeetingResponse
                                .builder()
                                .id(meeting.getMeetingId())
                                .title(meeting.getMeetingTitle())
                                .startDate(meeting.getStartDate())
                                .participants(findParticipants(meeting))
                                .build()).toList())
                .build();
    }

    public ReadWeeklyResponse makeWeeklyResponse(List<Sprint> sprints, List<Meeting> meetings, List<Task> tasks, List<Issue> issues) {
        return ReadWeeklyResponse
                .builder()
                .sprints(sprints.stream().map(
                        sprint -> SprintResponse
                                .builder()
                                .id(sprint.getSprintId())
                                .title(sprint.getSprintTitle())
                                .startDate(sprint.getStartDate())
                                .endDate(sprint.getEndDate())
                                .build()).toList())
                .meetings(meetings.stream().map(
                        meeting -> MeetingResponse
                                .builder()
                                .id(meeting.getMeetingId())
                                .title(meeting.getMeetingTitle())
                                .startDate(meeting.getStartDate())
                                .participants(findParticipants(meeting))
                                .build()).toList())
                .tasks(
                        tasks.stream().map(
                                task -> TaskResponse
                                        .builder()
                                        .id(task.getTaskId())
                                        .title(task.getTaskTitle())
                                        .endDate(task.getEndDate())
                                        .priority(task.getPriority())
                                        .labels(findLabels(task))
                                        .build()).toList())
                .issues(
                        issues.stream().map(
                                issue -> IssueResponse
                                        .builder()
                                        .title(issue.getIssueTitle())
                                        .description(issue.getIssueContents())
                                        .managerId(issue.getManager().getUserName())
                                        .build()).toList())
                .build();
    }

    public List<Participant> findParticipants(Meeting meeting) {
        return meeting.getMeetingParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .isManager(true)
                        .build()).toList();
    }

    public List<Label> findLabels(Task task) {
        return task.getTaskLabelSelects().stream().map(
                label -> Label
                        .builder()
                        .id(label.getTaskLabel().getTaskLabelId())
                        .labelName(label.getTaskLabel().getLabelName())
                        .color(label.getTaskLabel().getColor())
                        .build()).toList();
    }


}
