package gtl.proccessor.email_proccessor.repositories;

import gtl.proccessor.email_proccessor.models.EmailNotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotificationModel, Long> {
}
