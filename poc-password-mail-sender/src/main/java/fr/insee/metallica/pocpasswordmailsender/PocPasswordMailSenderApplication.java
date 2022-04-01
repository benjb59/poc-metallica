package fr.insee.metallica.pocpasswordmailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class PocPasswordMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocPasswordMailSenderApplication.class, args);
	}

}
