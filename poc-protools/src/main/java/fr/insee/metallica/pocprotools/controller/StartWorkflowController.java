package fr.insee.metallica.pocprotools.controller;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.insee.metallica.pocprotools.domain.Workflow;
import fr.insee.metallica.pocprotools.service.WorkflowEngine;

@RestController
public class StartWorkflowController {
	static Logger log = LoggerFactory.getLogger(StartWorkflowController.class);
	static public class UsernameDto {
		@NotBlank
		String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
	
	@Autowired
	private WorkflowEngine workflowEngine;
	
	@PostMapping(path = "/start-workflow")
	public CompletableFuture<String> startWorkflow(@Valid @RequestBody UsernameDto dto) {
		try {
			return workflowEngine.startWorkflowAndWait(Workflows.GeneratePasswordAndSendMail, dto)
			.thenApply((result) -> "Message Sent for user " +  dto.getUsername());
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not serialize the Dto");
		}
	}
	
	@PostMapping(path = "/start-workflow-async")
	public Workflow startWorkflowAsync(@Valid @RequestBody UsernameDto dto) {
		try {
			return workflowEngine.startWorkflow(Workflows.GeneratePasswordAndSendMail, dto);
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not serialize the Dto");
		}
	}
}
