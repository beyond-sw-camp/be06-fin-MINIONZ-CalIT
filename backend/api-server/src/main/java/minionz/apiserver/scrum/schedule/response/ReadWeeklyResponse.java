package minionz.apiserver.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReadWeeklyResponse {
    private List<SprintResponse> sprints;
    private List<MeetingResponse> meetings;
    private List<TaskResponse> tasks;
    private List<IssueResponse> issues;
}
