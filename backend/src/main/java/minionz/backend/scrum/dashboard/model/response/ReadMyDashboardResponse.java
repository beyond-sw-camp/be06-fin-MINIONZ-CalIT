package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class ReadMyDashboardResponse {
    private MyProgressResponse progress;
    private List<PriorityTaskResponse> priorityTasks;
    private List<UpcomingMyMeetingResponse> upcomingMeetings;
}
