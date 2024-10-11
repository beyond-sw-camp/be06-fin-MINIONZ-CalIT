package minionz.apiserver.scrum.label.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateMeetingLabelRequest {
    private Long meetingId;
    private Long workspaceId;
    private String labelName;
    private String description;
    private String color;
}