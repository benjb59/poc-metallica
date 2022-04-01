package fr.insee.metallica.pocprotools.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.insee.metallica.pocprotools.domain.Workflow;
import fr.insee.metallica.pocprotools.domain.Workflow.Step;

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
	@Transactional
	default public Workflow createWorkflow(Workflow.Type type, Workflow.Step step) {
		var workflow = new Workflow();
		workflow.setType(type);
		workflow.setStep(step);
		return save(workflow);
	}

	@Transactional
	default public Workflow updateStep(UUID workflowId, Step step) {
		var workflow = findById(workflowId).orElseThrow();
		workflow.setStep(step);
		return save(workflow);
	}
}
