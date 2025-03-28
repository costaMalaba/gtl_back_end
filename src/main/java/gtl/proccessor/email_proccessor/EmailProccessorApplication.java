package gtl.proccessor.email_proccessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class EmailProccessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailProccessorApplication.class, args);
	}

}
