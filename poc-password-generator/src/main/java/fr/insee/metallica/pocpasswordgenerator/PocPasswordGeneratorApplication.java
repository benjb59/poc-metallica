package fr.insee.metallica.pocpasswordgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE)
public class PocPasswordGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocPasswordGeneratorApplication.class, args);
	}

}
