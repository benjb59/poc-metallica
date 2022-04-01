package fr.insee.metallica.pocpasswordmailsender.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.insee.metallica.pocpasswordmailsender.service.MailSenderService;
import fr.insee.metallica.pocpasswordmailsender.service.MailSenderService.CouldNotSendMailException;

@RestController
public class SendMailController {
	static public class MailDto {
		@NotEmpty
		String username;

		@NotEmpty
		String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@PostMapping(path = "/send-mail")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void sendMail(@Valid @RequestBody MailDto dto) {
		try {
			mailSenderService.sendPasswordMail(dto.getUsername(), dto.getPassword());
		} catch (CouldNotSendMailException e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Erreur lors de l'envoi de mail");
		}
	}
	
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "/all-mails")
	@ResponseBody
	public String getAllSentBodies() {
		try {
			return mailSenderService.getAll();
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la lecture des mails envoyés");
		}
	}
}
