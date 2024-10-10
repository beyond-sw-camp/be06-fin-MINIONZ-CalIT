package minionz.apiserver.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReadMonthlyResponse {
    private List<SprintResponse> sprints;
    private List<MeetingResponse> meetings;
}
