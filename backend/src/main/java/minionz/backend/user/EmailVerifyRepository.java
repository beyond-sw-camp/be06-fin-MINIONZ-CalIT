package minionz.backend.user;

import minionz.backend.user.model.EmailVerify;
import minionz.backend.user.model.request.UuidRequest;
import org.apache.kafka.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    EmailVerify findByUuid(String uuid);

    EmailVerify findByEmail(String email);
}