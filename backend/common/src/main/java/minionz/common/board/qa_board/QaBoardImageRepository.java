package minionz.common.board.qa_board;

import minionz.common.board.qa_board.model.QaBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QaBoardImageRepository extends JpaRepository<QaBoardImage, Long> {

    @Query("SELECT qi FROM QaBoardImage qi " +
            "WHERE qi.qaBoard.qaBoardId = :qaBoardId")
    Optional<List<QaBoardImage>> findByQaBoardId(Long qaBoardId);
}
