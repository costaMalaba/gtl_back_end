package gtl.proccessor.email_proccessor.services.servicesImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gtl.proccessor.email_proccessor.dto.EmailReqDto;
import gtl.proccessor.email_proccessor.models.EmailNotificationModel;
import gtl.proccessor.email_proccessor.repositories.EmailNotificationRepository;
import gtl.proccessor.email_proccessor.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;
    private final EmailNotificationRepository emailNotificationRepository;

    @RabbitListener(queues = "emailQueue")
    @Async
    public void processEmail(String emailReq) throws InterruptedException, JsonProcessingException {
        log.info("Email received");

        ObjectMapper objectMapper = new ObjectMapper();
        EmailReqDto req = objectMapper.readValue(emailReq, EmailReqDto.class);
        emailService.sendEmail(req.name(), req.email(), req.message());
        log.info("Email sent");

        // Save data
        saveData(req.name(), req.email(), req.message());

        // Sleep for 1 second
        Thread.sleep(1000);
    }

    private void saveData(String name, String email, String message) {
        emailNotificationRepository.save(
                EmailNotificationModel.builder()
                        .name(name)
                        .email(email)
                        .message(message)
                        .build()
        );
    }
}
