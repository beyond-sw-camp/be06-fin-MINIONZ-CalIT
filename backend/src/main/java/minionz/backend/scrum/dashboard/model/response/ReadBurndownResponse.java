package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ReadBurndownResponse {
    private Long sprintId;
    private String sprintName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<BurnTaskResponse> tasks;
    private double burndownPercentage;

}
