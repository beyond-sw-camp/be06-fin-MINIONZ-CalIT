package minionz.apiserver.search.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserResponse {
    private Long searchUserIdx;
    private String loginId;
    private String userName;
    private String email;
    private Integer persona;
}
