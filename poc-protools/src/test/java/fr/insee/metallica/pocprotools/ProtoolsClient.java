package fr.insee.metallica.pocprotools;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.insee.metallica.pocprotools.controller.StartWorkflowController.UsernameDto;

@FeignClient(value = "protools", url = "${urls.protools}")
public interface ProtoolsClient {
	@PostMapping(path = "/start-workflow")
	public String startWorkflow(@RequestBody UsernameDto dto);
}
