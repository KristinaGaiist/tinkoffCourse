package unit2.task1.cmd;

import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;

public class AddEmployeeCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "addEmployee";
	}

	@Override
	public String getDescription() {
		return "Для добавления сотрудника введите команду addEmployee arg1 arg2, где arg1 - имя сотрудника, arg2 - "
			+ "фамилия сотрудника.";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		if (arguments.length != 2) {
			System.out.println(Messages.ILLEGAL_ARGUMENTS + Messages.TRY_AGAIN);
			return;
		}
		office.addEmployee(new Employee(arguments[0], arguments[1]));
	}
}
