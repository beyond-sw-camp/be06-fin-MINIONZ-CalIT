package minionz.apiserver.user.model.response;

import lombok.AllArgsConstructor;
import minionz.apiserver.user.model.OAuth2UserInfo;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserDetails implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    public String getRole() {
        return (String) attributes.get("role");
    }
}
