package minionz.common.scrum.sprint.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minionz.common.scrum.sprint.model.QSprint;
import minionz.common.scrum.sprint.model.Sprint;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SprintRepositoryCustomImpl implements SprintRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Sprint> findSprintsInPeriod(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate) {
        QSprint sprint = QSprint.sprint;
        return queryFactory.selectFrom(sprint)
                .where(sprint.workspace.workspaceId.eq(workspaceId)
                        .and(sprint.startDate.loe(endDate))
                        .and(sprint.endDate.goe(startDate)))
                .fetch();
    }

    @Override
    public List<Sprint> findMySprintsInPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        QSprint sprint = QSprint.sprint;
        return queryFactory.selectFrom(sprint)
                .join(sprint.sprintParticipations).fetchJoin()
                .where(sprint.sprintParticipations.any().user.userId.eq(userId)
                        .and(sprint.startDate.loe(endDate))
                        .and(sprint.endDate.goe(startDate)))
                .fetch();
    }

    @Override
    public int findInprogressSprintCount(Long workspaceId) {
        QSprint sprint = QSprint.sprint;
        BooleanBuilder builder = new BooleanBuilder();
        LocalDateTime today = LocalDateTime.now();

        if (workspaceId != null) {
            builder.and(sprint.workspace.workspaceId.eq(workspaceId));
        }
        // 오늘 날짜가 포함된 스프린트 카운트
        builder.and(sprint.startDate.loe(today))
                .and(sprint.endDate.goe(today));

        Long count = queryFactory.select(sprint.count())
                .from(sprint)
                .where(builder)
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }


    @Override
    public int findAllSprintCount(Long workspaceId) {
        QSprint sprint = QSprint.sprint;
        Long count = queryFactory.select(sprint.count())
                .from(sprint)
                .where(sprint.workspace.workspaceId.eq(workspaceId))
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }

}
