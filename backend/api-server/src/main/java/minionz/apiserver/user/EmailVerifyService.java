package minionz.apiserver.user;


import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.user.model.EmailVerify;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailVerifyService {
    private final JavaMailSender mailSender;

    public EmailVerifyService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("이메일 인증");
        mailMessage.setText("인증코드 : " + verificationCode);

        mailSender.send(mailMessage);
    }
}