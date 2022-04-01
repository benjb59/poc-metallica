package fr.insee.metallica.pocpasswordgenerator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(indexes = @Index(columnList = "username", unique = true))
public class PasswordHash {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String passwordSsha;
	
	@Version
	private int version;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getVersion() {
		return version;
	}
	public String getPasswordSsha() {
		return passwordSsha;
	}
	public void setPasswordSsha(String passwordSsha) {
		this.passwordSsha = passwordSsha;
	}
	
 }
