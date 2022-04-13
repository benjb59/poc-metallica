package fr.insee.metallica.pocprotools.command.service;

import fr.insee.metallica.pocprotools.command.domain.Command;

public interface CommandEventListener {
	public static enum Type {
		Added, Aquired, Processing, Done, Error, Retry
	}
	
	public void onEvent(Command command, Object result);
}
