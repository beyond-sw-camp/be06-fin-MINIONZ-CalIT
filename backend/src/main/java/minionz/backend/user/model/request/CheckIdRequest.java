package minionz.backend.user.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckIdRequest {
    private String loginId;
}
