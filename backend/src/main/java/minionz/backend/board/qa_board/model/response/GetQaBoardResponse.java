package minionz.backend.board.qa_board.model.response;

import jakarta.persistence.EntityListeners;
import lombok.*;
import minionz.backend.board.qa_board.model.AnswerStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class GetQaBoardResponse {
    private Long qaBoardId;
    private String qaboardTitle;
    private String qaboardContent;
    private Integer personaImage;

    private Long workspaceId;
    private AnswerStatus answerStatus; //답변상태

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GetQaBoardImageResponse> getQaBoardImageResponseList;

    private String userName;
    private String taskName;
    private String assignUser;
}
