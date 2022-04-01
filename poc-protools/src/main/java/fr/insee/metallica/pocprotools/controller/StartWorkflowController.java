package fr.insee.metallica.pocprotools.controller;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import feign.FeignException;
import fr.insee.metallica.pocprotools.client.PasswordGenerateClient;
import fr.insee.metallica.pocprotools.client.SendMailClient;
import fr.insee.metallica.pocprotools.domain.Workflow.Step;
import fr.insee.metallica.pocprotools.domain.Workflow.Type;
import fr.insee.metallica.pocprotools.repository.WorkflowRepository;

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
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private PasswordGenerateClient passwordGenerateClient;
	
	@Autowired
	private SendMailClient sendMailClient;
	
	@Autowired
	private WorkflowRepository workflowRepository;
	
	@PostMapping(path = "/start-workflow")
	public String startWorkflow(@Valid @RequestBody UsernameDto dto) {
		var username = dto.getUsername();
		var workflow = workflowRepository.createWorkflow(Type.GenerateAndSendPassword, Step.GenerateAndSendPassword_Received);

		var workflowContext = mapper.createObjectNode();
		workflowContext.set("username", TextNode.valueOf(username));
		
		try {
			workflowRepository.updateStep(workflow.getWorkflowId(), Step.GenerateAndSendPassword_Generate);
			var result = passwordGenerateClient.generatePassword(workflowContext);
			workflowRepository.updateStep(workflow.getWorkflowId(), Step.GenerateAndSendPassword_Generate_Done);

			for (var property : List.of("password")) {
				workflowContext.set(property, result.get(property));
			}
		} catch (FeignException e) {
			// we should handle that correctly
			log.error("Generate password threw something", e);
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Generate password threw something", e); 
		}
		
		try {
			workflowRepository.updateStep(workflow.getWorkflowId(), Step.GenerateAndSendPassword_Mail);
			sendMailClient.sendMail(workflowContext);
			workflowRepository.updateStep(workflow.getWorkflowId(), Step.GenerateAndSendPassword_Mail_Done);
		} catch (FeignException e) {
			// we should handle that correctly
			log.error("send mail threw something", e);
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "send mail threw something", e); 
		}
		
		return "Message Sent for user " +  dto.getUsername();
	}
}
