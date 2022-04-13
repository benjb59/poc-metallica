package fr.insee.metallica.pocprotools.command.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.insee.metallica.pocprotools.command.domain.Command;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionException;

@Service
public class PrintCommandProcessor extends AbstractStringCommandProcessor {
	private static final Logger log = LoggerFactory.getLogger(PrintCommandProcessor.class);
	
	public PrintCommandProcessor() {
		super(Processors.Print);
	}

	@Override
	public Object process(Command command, String payload) throws CommandExecutionException {
		log.info(payload);
		return null;

	}

}
