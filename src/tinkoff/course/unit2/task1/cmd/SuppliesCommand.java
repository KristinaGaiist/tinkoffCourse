package unit2.task1.cmd;

import java.util.Optional;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.task2.OfficeSupply;

public class SuppliesCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "supplies";
	}

	@Override
	public String getDescription() {
		return "Для отображения всех канцтоваров у определенного сотрудника введите команду supplies arg1, где "
			+ "arg1 - id сотрудника";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		if (arguments.length != 1) {
			System.out.println(Messages.ILLEGAL_ARGUMENTS + "\n" + Messages.TRY_AGAIN);
			return;
		}

		int id;
		try {
			id = Integer.parseInt(arguments[0]);
		} catch (NumberFormatException e) {
			System.out.println(Messages.ILLEGAL_EMPLOYEE_ID + Messages.TRY_AGAIN);
			return;
		}

		Optional<Employee> employee = office.getEmployeeById(id);
		if (employee.isEmpty()) {
			System.out.println(String.format(Messages.EMPLOYEE_NOT_FOUND, id) + Messages.TRY_AGAIN);
			return;
		}

		for (OfficeSupply supply : employee.get().getWorkPlace().getOfficeSupplies()) {
			System.out.println(supply);
		}
	}
}
