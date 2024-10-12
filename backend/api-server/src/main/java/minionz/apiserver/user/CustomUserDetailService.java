package minionz.apiserver.user;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public CustomSecurityUserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId);

        if (user != null) {
            return new CustomSecurityUserDetails(user);
        } else {
            return null;
        }
    }
}
