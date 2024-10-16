package minionz.apiserver.user.model;

import lombok.Getter;
import lombok.Setter;
import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
import minionz.common.scrum.sprint_participation.model.SprintParticipation;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomOauth2UserDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

    public CustomOauth2UserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<WorkspaceParticipation> workspaceParticipations = user.getWorkspaceParticipations();
        List<SprintParticipation> sprintParticipations = user.getSprintParticipations();
        List<MeetingParticipation> meetingParticipations = user.getMeetingParticipations();

        List<GrantedAuthority> authorities = new ArrayList<>();

// workspaceParticipations 처리
        if (workspaceParticipations != null) {
            authorities.addAll(workspaceParticipations.stream()
                    .filter(participate -> participate.getIsValid())
                    .map(participate -> {
                        String rolePrefix = participate.getIsManager() ? "ROLE_WORKSPACE_ADMIN_" : "ROLE_WORKSPACE_MEMBER_";
                        String roleName = rolePrefix + participate.getWorkspace().getWorkspaceId();
                        return new SimpleGrantedAuthority(roleName);
                    })
                    .collect(Collectors.toList()));
        }

// sprintParticipations 처리
        if (sprintParticipations != null) {
            authorities.addAll(sprintParticipations.stream()
                    .map(participate -> {
                        String rolePrefix = participate.getIsManager() ? "ROLE_SPRINT_ADMIN_" : "ROLE_SPRINT_MEMBER_";
                        String roleName = rolePrefix + participate.getSprint().getSprintId();
                        return new SimpleGrantedAuthority(roleName);
                    })
                    .collect(Collectors.toList()));
        }

// meetingParticipations 처리
        if (meetingParticipations != null) {
            authorities.addAll(meetingParticipations.stream()
                    .map(participate -> {
                        String roleName = "ROLE_MEETING_MEMBER_" + participate.getMeeting().getMeetingId();
                        return new SimpleGrantedAuthority(roleName);
                    })
                    .collect(Collectors.toList()));
        }

        // 사용자 역할 추가
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

