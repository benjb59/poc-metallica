package fr.insee.metallica.pocprotools.command.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Command {
	public static enum Status {
		Pending, Processing, Done, Error, Retry
	}
	
	@Id
	@Column(columnDefinition = "UUID")
	private UUID id = UUID.randomUUID();
	
	private String type;
	
	@LastModifiedDate
	private LocalDateTime lastUpdate;
	
	private LocalDateTime lastHeartBeat;

	private LocalDateTime nextScheduledTime;

	private int nbTry;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	@Lob
	private String context;
	
	@Lob
	private String payload;
	
	@Lob
	private String result;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getNbTry() {
		return nbTry;
	}

	public void setNbTry(int nbTry) {
		this.nbTry = nbTry;
	}

	public LocalDateTime getNextScheduledTime() {
		return nextScheduledTime;
	}

	public void setNextScheduledTime(LocalDateTime nextScheduledTime) {
		this.nextScheduledTime = nextScheduledTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(LocalDateTime lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}
	
}
