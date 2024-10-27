package minionz.apiserver.scrum.schedule.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadScheduleRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
