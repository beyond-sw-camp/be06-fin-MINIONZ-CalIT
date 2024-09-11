package minionz.backend.scrum.dashboard;

import lombok.RequiredArgsConstructor;
import minionz.backend.scrum.dashboard.model.request.ReadMyDashboardRequest;
import minionz.backend.scrum.dashboard.model.response.PriorityTaskResponse;
import minionz.backend.scrum.dashboard.model.response.ProgressResponse;
import minionz.backend.scrum.dashboard.model.response.ReadMyDashboardResponse;
import minionz.backend.scrum.dashboard.model.response.UpcomingMyMeetingResponse;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
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

    public ReadMyDashboardResponse readMyDashboard(User user, ReadMyDashboardRequest request) {
        Long userId = user.getUserId();
        Pageable pageable = PageRequest.of(0, 3);

        ProgressResponse progress = ProgressResponse
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

        List<UpcomingMyMeetingResponse> upcomingMeetings = meetings.stream().map(
                meeting -> UpcomingMyMeetingResponse
                        .builder()
                        .id(meeting.getMeetingId())
                        .title(meeting.getMeetingTitle())
                        .workspaceName(meeting.getSprint().getWorkspace().getWorkspaceName())
                        .startDate(meeting.getStartDate())
                        .build()
        ).toList();

        return ReadMyDashboardResponse
                .builder()
                .progress(progress)
                .priorityTasks(priorityTasks)
                .upcomingMeetings(upcomingMeetings)
                .build();
    }


}
