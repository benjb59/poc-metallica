package fr.insee.metallica.pocpasswordmailsender.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	public static class CouldNotSendMailException extends Exception {
		private static final long serialVersionUID = 1L;
	}
	
	@Value("${DATA_FOLDER:}")
	private String dataFolder;
	
	private File smtpPretend;
	
	@PostConstruct
	public void init() {
		 var dir = StringUtils.isBlank(dataFolder) ? 
				 new File(FileUtils.getTempDirectory(), "poc-mailsender") :
				 new File(dataFolder);
		 dir.mkdirs();
		 smtpPretend = new File(dir, "mail-body.log");
	}
	
	@Transactional
	public void sendPasswordMail(String username, String password) throws CouldNotSendMailException {
		// we just append to a text file
		try {
			FileUtils.write(smtpPretend, String.format("Hello %s, your password has been generated %s\n", username, password), StandardCharsets.UTF_8, true);
		} catch (IOException e) {
			throw new CouldNotSendMailException();
		}
	}

	public String getAll() throws IOException {
		return FileUtils.readFileToString(smtpPretend, StandardCharsets.UTF_8);
	}
}
