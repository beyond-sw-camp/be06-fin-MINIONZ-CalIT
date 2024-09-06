package minionz.backend.error_board;



import minionz.backend.error_board.model.ErrorBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErrorBoardRepository extends JpaRepository<ErrorBoard, Long> {

    // 전체 조회
   Page<ErrorBoard> findAll(Pageable pageable);

}
