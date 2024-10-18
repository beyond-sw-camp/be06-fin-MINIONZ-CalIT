package minionz.apiserver.scrum.dashboard;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.apiserver.scrum.dashboard.model.response.*;
import minionz.common.scrum.issue.repository.IssueRepository;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.task.repository.TaskRepository;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.sprint.repository.SprintRepository;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.task.model.TaskStatus;
import minionz.common.scrum.workspace.WorkspaceRepository;
import minionz.common.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
                .allTaskCount(taskRepository.findMyTaskCount(userId, false))
                .successTaskCount(taskRepository.findMyTaskCount(userId, true))
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

        List<Task> tasks = taskRepository.findAllBySprintIdAndUserId(sprintId,null);

        // 전체 작업 수 계산
        int allTaskCount = tasks.size();

        // TODO와 IN_PROGRESS 상태의 작업 수 계산
        long inProgressAndTodoCount = tasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.TODO)
                        || task.getStatus().equals(TaskStatus.IN_PROGRESS))
                .count();

        // 백분율 계산
        double burndownPercentage = (allTaskCount > 0)
                ? ((double) inProgressAndTodoCount / allTaskCount) * 100
                : 0.0;

        // 스프린트 기간 계산
        LocalDateTime startDate = sprint.getStartDate();
        LocalDateTime endDate = sprint.getEndDate();
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        long elapsedDays = ChronoUnit.DAYS.between(startDate, LocalDateTime.now());


        if (elapsedDays < 1) {
            elapsedDays = 0;
        }
        // 남아있는 이상적인 작업량 계산 (선형 감소)
        double idealRemainingTasks = allTaskCount * (1 - ((double) elapsedDays / totalDays));

        // 이상적인 비율 계산 (선형 감소를 기준으로)
        double idealBurndownPercentage = (allTaskCount > 0)
                ? (idealRemainingTasks / allTaskCount) * 100
                : 0.0;

        // 응답 객체 생성
        return ReadBurndownResponse
                .builder()
                .sprintId(sprint.getSprintId())
                .sprintName(sprint.getSprintTitle())
                .startDate(startDate)
                .endDate(endDate)
                .tasks(tasks.stream().map(task -> BurnTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .status(task.getStatus())
                        .doneDate(task.getDoneDate())
                        .build()).toList())
                .burndownPercentage(burndownPercentage) // 현재 상태 비율
                .idealBurndownPercentage(idealBurndownPercentage) // 이상적인 상태 비율
                .build();
    }


}
