package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.exception.GenericRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String SENDER;

    private final JavaMailSender javaMailSender;

    public boolean sendMessage(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(SENDER);
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
            log.info("The verification code sent to email successfully................");
        } catch (Exception e) {
            log.error("There is an error when the verification code is going to the mail....");
            return false;
        }
        return true;
    }

    public String generateVerificationCode(String toEmail) {
        Random random = new Random();
        int otp = 0;
        for (int i = 0; i < 6; i++) {
            otp = otp * 10 + random.nextInt(10);
        }
        boolean isSent = sendMessage(toEmail, "Verification Code", otp + "");
        if (!isSent) {
            throw new GenericRuntimeException("verification.code.could.not.send");
        }
        return otp + "";
    }
}
