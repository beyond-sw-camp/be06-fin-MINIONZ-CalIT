package minionz.common.note.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursorMessage {
    private String senderId; // 누가 커서를 움직였는지
    private CursorPosition cursor; // 커서의 위치 정보
}
