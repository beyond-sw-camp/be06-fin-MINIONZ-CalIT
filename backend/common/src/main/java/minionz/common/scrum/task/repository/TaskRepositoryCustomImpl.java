package minionz.common.scrum.task.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minionz.common.scrum.task.model.QTask;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.task.model.TaskLevel;
import minionz.common.scrum.task.model.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public int findTaskCount(Long sprintId) {
        QTask task = QTask.task;
        Long count = queryFactory
                .select(task.count())
                .from(task)
                .where(task.sprint.sprintId.eq(sprintId))
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }

    @Override
    public List<Task> findMyTask(Long userId) {
        QTask task = QTask.task;
        return queryFactory.selectFrom(task)
                .join(task.taskParticipations).fetchJoin()
                .where(task.taskParticipations.any().user.userId.eq(userId)
                        .and(task.sprint.endDate.after(LocalDateTime.now())))
                .fetch();
    }


    @Override
    public List<Task> findPriorityMyTasks(Long userId, Pageable pageable) {
        QTask task = QTask.task;

        NumberExpression<Integer> priorityOrder = new CaseBuilder()
                .when(task.priority.eq(TaskLevel.HIGH)).then(1)
                .when(task.priority.eq(TaskLevel.MED)).then(2)
                .when(task.priority.eq(TaskLevel.LOW)).then(3)
                .otherwise(4);

        return queryFactory.selectFrom(task)
                .join(task.taskParticipations).fetchJoin()
                .where(task.taskParticipations.any().user.userId.eq(userId)
                        .and(task.sprint.endDate.after(LocalDateTime.now())))
                .orderBy(task.endDate.asc(), priorityOrder.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public int findAllTaskCount(Long workspaceId) {
        QTask task = QTask.task;
        Long count = queryFactory
                .select(task.count())
                .from(task)
                .join(task.sprint)
                .where(task.sprint.workspace.workspaceId.eq(workspaceId)
                        .and(task.sprint.endDate.after(LocalDateTime.now())))
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }

    @Override
    public int findSuccessTaskCount(Long workspaceId) {
        QTask task = QTask.task;
        Long count = queryFactory
                .select(task.count())
                .from(task)
                .join(task.sprint)
                .where(task.sprint.workspace.workspaceId.eq(workspaceId)
                        .and(task.sprint.endDate.after(LocalDateTime.now()))
                        .and(task.status.eq(TaskStatus.DONE)))
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }

    @Override
    public int findMyTaskCount(Long userId, boolean done) {
        QTask task = QTask.task;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(task.taskParticipations.any().user.userId.eq(userId))
                .and(task.sprint.endDate.after(LocalDateTime.now()));

        if (done) {
            builder.and(task.status.eq(TaskStatus.DONE));
        }

        Long count = queryFactory
                .select(task.count())
                .from(task)
                .where(builder)
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }

    @Override
    public List<Task> findUpcomingWorkspaceTasks(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QTask task = QTask.task;

        return queryFactory.selectFrom(task)
                .join(task.sprint).fetchJoin()
                .join(task.sprint.workspace).fetchJoin()
                .where(task.sprint.workspace.workspaceId.eq(workspaceId)
                        .and(task.startDate.loe(endDate))
                        .and(task.endDate.goe(startDate)))
                .orderBy(task.endDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Task> findUpcomingMyTasks(Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QTask task = QTask.task;

        return queryFactory.selectFrom(task)
                .join(task.taskParticipations).fetchJoin()
                .join(task.sprint).fetchJoin()
                .where(task.taskParticipations.any().user.userId.eq(userId)
                        .and(task.startDate.loe(endDate))
                        .and(task.endDate.goe(startDate)))
                .orderBy(task.endDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Task> findAllBySprintIdAndUserId(Long sprintId, Long userId) {
        QTask task = QTask.task;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(task.sprint.sprintId.eq(sprintId));

        if (userId != null) {
            builder.and(task.taskParticipations.any().user.userId.eq(userId));
        }

        return queryFactory.selectFrom(task)
                .join(task.sprint).fetchJoin()
                .join(task.sprint.workspace).fetchJoin()
                .where(builder)
                .fetch();
    }

    @Override
    public List<Task> findAllByWorkspaceId(Long workspaceId) {
        QTask task = QTask.task;
        BooleanBuilder builder = new BooleanBuilder();

        // Null 체크 추가
        if (workspaceId != null) {
            builder.and(task.sprint.workspace.workspaceId.eq(workspaceId));
        }

        return queryFactory.selectFrom(task)
                .join(task.sprint).fetchJoin()
                .join(task.sprint.workspace).fetchJoin()
                .where(builder)
                .fetch();
    }


}
