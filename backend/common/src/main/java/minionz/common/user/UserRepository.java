package minionz.common.user;

import minionz.common.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //아이디를 찾아 존재 여부 판단
    boolean existsByLoginId(String loginId);
    //아이디를 찾고 해당하는 USER 객체 반환
    User findByLoginId(String loginId);

    User findByEmail(String email);
}
