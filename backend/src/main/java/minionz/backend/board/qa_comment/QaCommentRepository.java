package minionz.backend.board.qa_comment;

import minionz.backend.board.qa_board.model.QaBoard;
import minionz.backend.board.qa_comment.model.QaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QaCommentRepository extends JpaRepository<QaComment,Long> {

    Optional<QaComment> findById(Long id);
    List<QaComment> findByQaBoard(QaBoard qaBoard);
}
