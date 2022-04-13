package fr.insee.metallica.pocprotools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableTransactionManagement
public class PocProtoolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocProtoolsApplication.class, args);
	}

}
