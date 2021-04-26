package unit2.task1.exception;

import unit2.task1.cmd.Messages;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String commandName) {
        super(String.format(Messages.COMMAND_NOT_FOUND, commandName));
    }
}
