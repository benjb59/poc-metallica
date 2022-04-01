package fr.insee.metallica.pocpasswordgenerator.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.metallica.pocpasswordgenerator.domain.PasswordHash;
import fr.insee.metallica.pocpasswordgenerator.repository.PasswordHashRepository;

@Service
public class PasswordHashService {
	@Autowired
	private PasswordHashRepository passwordHashRepository;
	
	@Transactional
	public PasswordHash hashAndSave(String username, String password) {
		return passwordHashRepository.hashAndSave(username, password);
	}

	public List<PasswordHash> findAll() {
		return passwordHashRepository.findAll();
	}
}
