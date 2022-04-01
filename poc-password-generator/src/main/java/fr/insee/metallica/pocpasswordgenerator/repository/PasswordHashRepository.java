package fr.insee.metallica.pocpasswordgenerator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.insee.metallica.pocpasswordgenerator.domain.PasswordHash;
import fr.insee.metallica.pocpasswordgenerator.networkmesser.NetworkMesser;

public interface PasswordHashRepository extends JpaRepository<PasswordHash, UUID> {
	@NetworkMesser("repository")
	default public PasswordHash hashAndSave(String username, String password) {
		var passwordHash = new PasswordHash();
		passwordHash.setUsername(username);
		passwordHash.setPasswordSsha(new BCryptPasswordEncoder().encode(password));
		return save(passwordHash);
	}
}
