package minionz.common.note;

import minionz.common.note.model.NoteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteImageRepository extends JpaRepository<NoteImage, Long> {

    @Query("SELECT ni FROM NoteImage ni " +
            "WHERE ni.note.noteId = :noteId")
    Optional<List<NoteImage>> findByNoteId(Long noteId);
}