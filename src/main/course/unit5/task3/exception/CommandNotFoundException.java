package unit5.task3.exception;

import unit5.task3.ExceptionMessage;

public class CommandNotFoundException extends ValidationException {

    public CommandNotFoundException(String commandName) {
        super(String.format(ExceptionMessage.COMMAND_NOT_FOUND, commandName));
    }
}
