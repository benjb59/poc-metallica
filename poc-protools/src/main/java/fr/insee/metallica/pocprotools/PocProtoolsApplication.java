package fr.insee.metallica.pocprotools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.insee.metallica.pocprotools.repository.WorkflowRepository;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackageClasses = WorkflowRepository.class)
@EnableTransactionManagement
public class PocProtoolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocProtoolsApplication.class, args);
	}

}
