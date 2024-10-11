package minionz.common.alarm.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadMyAlarmResponse {
    private String title;
    private String content;
    private Long idx;
    private Long type;
    private LocalDateTime time;
}
