package fr.insee.metallica.pocprotools.command.processor;

import org.springframework.stereotype.Service;

import fr.insee.metallica.pocprotools.command.domain.Command;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionException;

@Service
public class DoNothingCommandProcessor extends AbstractCommandProcessor {
	public DoNothingCommandProcessor() {
		super(Processors.DoNothing);
	}

	@Override
	public Object process(Command command) throws CommandExecutionException {
		return null;
	}
}
