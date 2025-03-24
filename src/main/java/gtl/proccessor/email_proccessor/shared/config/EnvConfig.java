package gtl.proccessor.email_proccessor.shared.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EnvConfig {

    @Value("${app.x-api-key}")
    private String apiKey;
}
