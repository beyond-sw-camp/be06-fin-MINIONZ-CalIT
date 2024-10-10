package minionz.apiserver.board.error_board;
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

    // 전체 조회
    Page<ErrorBoard> findAll(Pageable pageable);

    Optional<ErrorBoard> findById(Long id);

    // 검색어 기반으로 전체 조회
    @Query("SELECT e FROM ErrorBoard e " +
            "WHERE e.errorboardTitle LIKE %:keyword% " +
            "OR e.errorboardContent LIKE %:keyword%")
    Optional<Page<ErrorBoard>> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 카테고리별 조회
    @Query("SELECT e FROM ErrorBoard e WHERE e.category = :category")
    Page<ErrorBoard> findByCategory(@Param("category") ErrorBoardCategory category, Pageable pageable);
}
