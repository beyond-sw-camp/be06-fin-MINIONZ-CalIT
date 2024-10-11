package minionz.apiserver.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReadWorkspaceDashboardResponse {
    private WorkspaceProgressResponse progress;
    private List<UpcomingMyMeetingResponse> upcomingMeetings;
}
