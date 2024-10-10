package minionz.apiserver.user;

import minionz.apiserver.user.model.EmailVerify;
import minionz.apiserver.user.model.request.UuidRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    EmailVerify findByUuid(String uuid);

    EmailVerify findByEmail(String email);
}