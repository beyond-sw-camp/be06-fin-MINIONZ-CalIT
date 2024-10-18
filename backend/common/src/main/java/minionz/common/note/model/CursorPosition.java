package minionz.common.note.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursorPosition {
    private int index;  // 커서가 위치한 인덱스
    private int length; // 선택된 텍스트의 길이
}
