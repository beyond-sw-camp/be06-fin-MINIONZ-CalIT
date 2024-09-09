package minionz.backend.board.error_comment;

import minionz.backend.board.error_comment.model.ErrorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ErrorCommnetRepository extends JpaRepository<ErrorComment,Long> {

    Optional<ErrorComment> findById(Long id);

}
