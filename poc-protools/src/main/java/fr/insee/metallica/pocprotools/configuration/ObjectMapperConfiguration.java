package fr.insee.metallica.pocprotools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfiguration {
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
