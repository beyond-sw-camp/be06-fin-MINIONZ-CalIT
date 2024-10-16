package minionz.common.board.qa_board;

import minionz.common.board.qa_board.model.QaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QaBoardRepository extends JpaRepository<QaBoard, Long> {


    Optional<QaBoard> findById(Long id);

    // 전체 조회 (workspaceId 기준으로 필터링)
    @Query("SELECT q FROM QaBoard q WHERE q.workSpace.workspaceId = :workspaceId")
    Page<QaBoard> findAllByWorkspaceId(@Param("workspaceId") Long workspaceId, Pageable pageable);


    // 검색어 기반으로 전체 조회 (workspaceId 기준으로 필터링)
    @Query("SELECT q FROM QaBoard q WHERE (q.qaboardTitle LIKE %:keyword% OR q.qaboardContent LIKE %:keyword%) AND q.workSpace.workspaceId = :workspaceId")
    Optional<Page<QaBoard>> findByKeyword(@Param("keyword") String keyword, @Param("workspaceId") Long workspaceId, Pageable pageable);

}
