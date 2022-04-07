package fr.insee.metallica.pocprotools.command.processor;

import fr.insee.metallica.pocprotools.command.domain.Command;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionException;

public interface CommandProcessor {
	Object process(Command command) throws CommandExecutionException;
}
