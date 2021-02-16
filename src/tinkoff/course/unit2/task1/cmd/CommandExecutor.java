package unit2.task1.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import unit2.task1.domain.Office;

public class CommandExecutor {

	private final List<ICommand> commands;

	public CommandExecutor() {
		this.commands = new ArrayList<>();
	}

	public void add(ICommand command) {
		this.commands.add(command);
	}

	public void execute(Office office, String[] arguments) {
		String name = arguments[0];
		if(name.equals("help")) {
			help();
			return;
		}
		Optional<ICommand> command = commands
			.stream()
			.filter(c -> c.getCommandName().equals(name))
			.findFirst();

		if (command.isEmpty()) {
			System.out.println(Messages.COMMAND_NOT_FOUND + Messages.TRY_AGAIN);
		} else {
			command.get().handle(office, Arrays.stream(arguments).skip(1).toArray(String[]::new));
		}
	}

	public void help() {
		commands.forEach(command -> System.out.println(command.getDescription()));
	}
}
