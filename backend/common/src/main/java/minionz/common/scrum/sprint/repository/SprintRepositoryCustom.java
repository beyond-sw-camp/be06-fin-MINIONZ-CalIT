package minionz.common.scrum.sprint.repository;

import minionz.common.scrum.sprint.model.Sprint;

import java.time.LocalDateTime;
import java.util.List;

public interface SprintRepositoryCustom {
    List<Sprint> findSprintsInPeriod(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate);

    List<Sprint> findMySprintsInPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    int findInprogressSprintCount(Long workspaceId);

    int findAllSprintCount(Long workspaceId);
}
