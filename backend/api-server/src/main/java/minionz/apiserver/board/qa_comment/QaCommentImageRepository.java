package minionz.apiserver.board.qa_comment;

import minionz.common.board.qa_comment.model.QaComment;
import minionz.common.board.qa_comment.model.QaCommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QaCommentImageRepository extends JpaRepository<QaCommentImage,Long> {

    List<QaCommentImage> findByQaComment(QaComment qaComment);
    Optional<QaCommentImage> findById(Long id);
}
