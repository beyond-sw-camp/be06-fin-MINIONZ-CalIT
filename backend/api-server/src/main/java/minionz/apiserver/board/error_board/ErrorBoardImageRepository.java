package minionz.apiserver.board.error_board;

import minionz.common.board.error_board.model.ErrorBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ErrorBoardImageRepository extends JpaRepository<ErrorBoardImage, Long> {

    @Query("SELECT bi FROM ErrorBoardImage bi " +
            "WHERE bi.errorBoard.errorBoardId = :errorBoardId")
    Optional<List<ErrorBoardImage>> findByErrorBoardId(Long errorBoardId);
}

