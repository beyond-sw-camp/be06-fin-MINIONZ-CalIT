package minionz.common.board.error_comment;
import minionz.common.board.error_comment.model.ErrorComment;
import minionz.common.board.error_comment.model.ErrorCommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ErrorCommentImageRepository extends JpaRepository<ErrorCommentImage,Long> {

    List<ErrorCommentImage> findByErrorComment(ErrorComment errorComment);
    Optional<ErrorCommentImage> findById(Long id);
}
