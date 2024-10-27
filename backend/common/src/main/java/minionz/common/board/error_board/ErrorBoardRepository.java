package minionz.common.board.error_board;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.error_board.model.ErrorBoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErrorBoardRepository extends JpaRepository<ErrorBoard, Long> {


    Optional<ErrorBoard> findById(Long id);

    // 전체 조회 (workspaceId 기준으로 필터링)
    @Query("SELECT e FROM ErrorBoard e WHERE e.workSpace.workspaceId = :workspaceId")
    Page<ErrorBoard> findAllByWorkspaceId(@Param("workspaceId") Long workspaceId, Pageable pageable);

    // 검색어 기반 조회 (workspaceId 기준으로 필터링)
    @Query("SELECT e FROM ErrorBoard e WHERE (e.errorboardTitle LIKE %:keyword% OR e.errorboardContent LIKE %:keyword%) AND e.workSpace.workspaceId = :workspaceId")
    Optional<Page<ErrorBoard>> findByKeyword(@Param("workspaceId") Long workspaceId,@Param("keyword") String keyword, Pageable pageable);

    // 카테고리별 조회 (workspaceId 기준으로 필터링)
    @Query("SELECT e FROM ErrorBoard e WHERE e.category = :category AND e.workSpace.workspaceId = :workspaceId")
    Page<ErrorBoard> findByCategory(@Param("category") ErrorBoardCategory category, @Param("workspaceId") Long workspaceId, Pageable pageable);
}
