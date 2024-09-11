package minionz.backend.scrum.dashboard;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.backend.scrum.dashboard.model.response.*;
import minionz.backend.scrum.issue.IssueRepository;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.sprint.SprintRepository;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.task.TaskRepository;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.workspace.WorkspaceRepository;
import minionz.backend.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final WorkspaceRepository workspaceRepository;
    private final TaskRepository taskRepository;
    private final MeetingRepository meetingRepository;
    private final IssueRepository issueRepository;
    private final SprintRepository sprintRepository;

    public ReadMyDashboardResponse readMyDashboard(User user, ReadMyDashboardRequest request) {
        Long userId = user.getUserId();
        Pageable pageable = PageRequest.of(0, 3);

        MyProgressResponse progress = MyProgressResponse
                .builder()
                .workspaceCount(workspaceRepository.findWorkspaceCountByUserId(userId))
                .allTaskCount(taskRepository.findMyAllTask(userId))
                .successTaskCount(taskRepository.findMyDoneTask(userId))
                .build();

        List<Task> tasks = taskRepository.findPriorityMyTasks(userId, pageable);

        List<PriorityTaskResponse> priorityTasks = tasks.stream().map(
                task -> PriorityTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .title(task.getTaskTitle())
                        .priority(task.getPriority())
                        .workspaceName(task.getSprint().getWorkspace().getWorkspaceName())
                        .endDate(task.getEndDate())
                        .taskNumber(task.getTaskNumber())
                        .build()
        ).toList();


        List<Meeting> meetings = meetingRepository.findMyMeetingsInPeriod(userId, request.getStartDate(), request.getEndDate());

        return ReadMyDashboardResponse
                .builder()
                .progress(progress)
                .priorityTasks(priorityTasks)
                .upcomingMeetings(toUpcomingMeetings(meetings))
                .build();
    }

    public ReadWorkspaceDashboardResponse readWorkspaceDashboard(Long workspaceId, ReadMyDashboardRequest request) {

        WorkspaceProgressResponse progress = WorkspaceProgressResponse
                .builder()
                .allSprintCount(sprintRepository.findAllSprintCount(workspaceId))
                .sprintCount(sprintRepository.findInprogressSprintCount(workspaceId, request.getStartDate(), request.getEndDate()))
                .allTaskCount(taskRepository.findAllTaskCount(workspaceId))
                .successTaskCount(taskRepository.findSuccessTaskCount(workspaceId))
                .issueCount(issueRepository.findWorkspaceIssuesCount(workspaceId))
                .build();

        List<Meeting> meetings = meetingRepository.findMeetingsInPeriod(workspaceId, request.getStartDate(), request.getEndDate());

        return ReadWorkspaceDashboardResponse
                .builder()
                .progress(progress)
                .upcomingMeetings(toUpcomingMeetings(meetings))
                .build();
    }

    public List<UpcomingMyMeetingResponse> toUpcomingMeetings(List<Meeting> meetings) {
        return meetings.stream().map(
                meeting -> UpcomingMyMeetingResponse
                        .builder()
                        .id(meeting.getMeetingId())
                        .title(meeting.getMeetingTitle())
                        .workspaceName(meeting.getSprint().getWorkspace().getWorkspaceName())
                        .sprintName(meeting.getSprint().getSprintTitle())
                        .startDate(meeting.getStartDate())
                        .build()
        ).toList();
    }

    public ReadBurndownResponse readBurndownChart(Long sprintId) throws BaseException {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.SPRINT_NOT_EXISTS));

        List<Task> tasks = taskRepository.findAllBySprintSprintId(sprintId);

        return ReadBurndownResponse
                .builder()
                .sprintId(sprint.getSprintId())
                .sprintName(sprint.getSprintTitle())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .tasks(tasks.stream().map(
                        task -> BurnTaskResponse
                                .builder()
                                .id(task.getTaskId())
                                .status(task.getStatus())
                                .doneDate(task.getDoneDate())
                                .build()).toList())
                .build();
    }

}
