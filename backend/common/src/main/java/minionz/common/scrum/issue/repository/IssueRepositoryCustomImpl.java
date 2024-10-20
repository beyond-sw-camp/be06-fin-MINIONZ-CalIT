package minionz.common.scrum.issue.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import minionz.common.scrum.issue.model.Issue;
import minionz.common.scrum.issue.model.QIssue;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IssueRepositoryCustomImpl implements IssueRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Issue> findUpcomingWorkspaceIssues(Long workspaceId, Pageable pageable) {
        QIssue issue = QIssue.issue;

        return queryFactory.selectFrom(issue)
                .join(issue.workspace).fetchJoin()
                .where(issue.workspace.workspaceId.eq(workspaceId)
                        .and(issue.status.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Issue> findUpcomingMyIssues(Long userId, Pageable pageable) {
        QIssue issue = QIssue.issue;

        return queryFactory.selectFrom(issue)
                .where(issue.manager.userId.eq(userId)
                        .and(issue.status.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public int findWorkspaceIssuesCount(Long workspaceId) {
        QIssue issue = QIssue.issue;
        Long count = queryFactory.select(issue.count())
                .from(issue)
                .where(issue.workspace.workspaceId.eq(workspaceId)
                        .and(issue.status.isTrue()))
                .fetchOne();

        return (count != null) ? count.intValue() : 0;
    }
}
