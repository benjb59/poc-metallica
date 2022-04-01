package fr.insee.metallica.pocprotools.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Workflow {
	public static enum Type {
		GenerateAndSendPassword
	}
	
	public static enum Step {
		GenerateAndSendPassword_Received,
		GenerateAndSendPassword_Generate,
		GenerateAndSendPassword_Generate_Done,
		GenerateAndSendPassword_Mail,
		GenerateAndSendPassword_Mail_Done,
	}
	
	@Id
	@Column(columnDefinition = "UUID")
	private UUID workflowId = UUID.randomUUID();
	@Enumerated(EnumType.STRING)
	private Type type;
	@Enumerated(EnumType.STRING)
	private Step step;
	
	@Version
	private int version;
	
	public int getVersion() {
		return version;
	}

	@Column(length = 8191)
	private String context;

	public UUID getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(UUID workflowId) {
		this.workflowId = workflowId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
