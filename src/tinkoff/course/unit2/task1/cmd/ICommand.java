package unit2.task1.cmd;

import unit2.task1.domain.Office;

public interface ICommand {

	String getCommandName();
	String getDescription();
	void handle(Office office, String[] arguments);
}
