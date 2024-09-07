package minionz.backend.board.error_comment;
import minionz.backend.board.error_comment.model.ErrorCommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErrorCommentImageRepository extends JpaRepository<ErrorCommentImage,Long> {

    Optional<ErrorCommentImage> findById(Long id);
}
