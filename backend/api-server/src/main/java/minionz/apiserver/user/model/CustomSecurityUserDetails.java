package minionz.apiserver.user.model;

import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
import minionz.common.scrum.sprint_participation.model.SprintParticipation;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Getter
@Setter
@RequiredArgsConstructor
public class CustomSecurityUserDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

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

        authorities.addAll(workspaceParticipations.stream()
                .filter(participate -> participate.getIsValid())
                .map(participate -> {
                    String rolePrefix = participate.getIsManager() ? "ROLE_WORKSPACE_ADMIN_" : "ROLE_WORKSPACE_MEMBER_";
                    String roleName = rolePrefix + participate.getWorkspace().getWorkspaceId();
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList()));

        authorities.addAll(sprintParticipations.stream()
                .map(participate -> {
                    String rolePrefix = participate.getIsManager() ? "ROLE_SPRINT_ADMIN_" : "ROLE_SPRINT_MEMBER_";
                    String roleName = rolePrefix + participate.getSprint().getSprintId();
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList()));

        authorities.addAll(meetingParticipations.stream()
                .map(participate -> {
                    String roleName = "ROLE_MEETING_MEMBER_" + participate.getMeeting().getMeetingId();
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList()));

        // 사용자 역할 추가
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return authorities;
    }
    public Long getUserId() {return user.getUserId(); }

    public String getLoginId() {
        return user.getLoginId();
    }

    public String getUserName() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
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
        return user.isEnabled();
    }

}