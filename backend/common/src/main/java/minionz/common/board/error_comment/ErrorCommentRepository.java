package minionz.common.board.error_comment;

import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.error_comment.model.ErrorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ErrorCommentRepository extends JpaRepository<ErrorComment,Long> {

    Optional<ErrorComment> findById(Long id);
    List<ErrorComment> findByErrorBoard(ErrorBoard errorBoard);
}
