package minionz.apiserver.user.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerifyRequest {
    private String email;
}