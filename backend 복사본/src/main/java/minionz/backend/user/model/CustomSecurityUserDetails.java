package minionz.backend.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import lombok.*;


@Builder
public class CustomSecurityUserDetails implements UserDetails {
    private final User user;

    public CustomSecurityUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                // 앞에 "ROLE_" 접두사 필수 !
                return "ROLE_" + user.getRole().name();
            }
        });

        return collection;
    }
    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    // user의 비밀번호 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public String getEmail() {
        return user.getEmail();
    }
    // user의 username 반환
    public String getUserName() {
        return user.getUserName();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
