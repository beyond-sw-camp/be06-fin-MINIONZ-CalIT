package minionz.backend.scrum.dashboard.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadMyDashboardRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
