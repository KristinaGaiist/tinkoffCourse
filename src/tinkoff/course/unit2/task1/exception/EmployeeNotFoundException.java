package unit2.task1.exception;

import unit2.task1.cmd.Messages;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(int id) {
        super(String.format(Messages.EMPLOYEE_NOT_FOUND, id));
    }
}
