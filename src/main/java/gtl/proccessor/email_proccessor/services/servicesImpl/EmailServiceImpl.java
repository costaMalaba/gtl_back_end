package gtl.proccessor.email_proccessor.services.servicesImpl;

import gtl.proccessor.email_proccessor.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String name, String email, String emailMessage) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("costantineyohana1999@gmail.com");
        message.setTo(email);
        message.setSubject("Notification");
        message.setText(emailMessage);
        mailSender.send(message);
    }
}
