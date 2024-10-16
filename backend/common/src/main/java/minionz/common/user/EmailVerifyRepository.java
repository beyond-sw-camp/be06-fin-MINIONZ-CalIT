package minionz.common.user;

import minionz.common.user.model.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    EmailVerify findByUuid(String uuid);

    EmailVerify findByEmail(String email);
}