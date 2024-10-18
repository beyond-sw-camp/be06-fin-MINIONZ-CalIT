package minionz.common.scrum.sprint.repository;

import minionz.common.scrum.sprint.model.Sprint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> , SprintRepositoryCustom {
    List<Sprint> findAllByWorkspaceWorkspaceId(Long id);
    Page<Sprint> findSprintsByEndDateAfter(LocalDateTime endDate, Pageable pageable);

}
