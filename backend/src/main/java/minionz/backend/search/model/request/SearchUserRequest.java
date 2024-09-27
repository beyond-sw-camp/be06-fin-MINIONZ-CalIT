package minionz.backend.search.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequest {
    private String username;

    public SearchUserRequest(String username){
        this.username = username;
    }
}
