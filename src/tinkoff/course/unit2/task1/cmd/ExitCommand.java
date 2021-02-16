package unit2.task1.cmd;

import unit2.task1.domain.Office;

public class ExitCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public String getDescription() {
		return "Введите команду exit для выхода.";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		System.exit(0);
	}
}
