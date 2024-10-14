package minionz.apiserver.user.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UuidRequest {
    private String uuid;
}
