package fr.insee.metallica.pocprotools.networkmesser;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Service
public class FeignInterceptor implements RequestInterceptor {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Set<String> doneUsernames = new HashSet<>();
	
	@Override
	public void apply(RequestTemplate template) {
		try {
			if (template.feignTarget().name().equals("password-generator")) {
				var object = mapper.readValue(template.body(), ObjectNode.class);
				if (object.get("username") != null) {
					var username = object.get("username").textValue();
					if (username.contains("unavailable") && doneUsernames.add(username)) {
						template.target("http://localhost:35554");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
