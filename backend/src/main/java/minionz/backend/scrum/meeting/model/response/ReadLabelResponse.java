package minionz.backend.scrum.meeting.model.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadLabelResponse {

    private Long id;
    private String labelName;
    private String color;
}
