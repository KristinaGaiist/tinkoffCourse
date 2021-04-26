package unit2.task1.exception;

import unit2.task1.cmd.Messages;

public class IllegalArgumentsSizeException extends RuntimeException {

    public IllegalArgumentsSizeException() {
        super(Messages.ILLEGAL_ARGUMENTS);
    }
}
