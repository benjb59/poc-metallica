package fr.insee.metallica.pocprotools.command.processor;

import fr.insee.metallica.pocprotools.command.domain.Command;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionException;

public abstract class AbstractStringCommandProcessor extends AbstractCommandProcessor {
	public AbstractStringCommandProcessor(String commandType) {
		super(commandType);
	}

	public Object process(Command command) throws CommandExecutionException {
		var payload = (String) command.getPayload();
		return process(command, payload);
	}

	public abstract Object process(Command command, String payload) throws CommandExecutionException;
}
