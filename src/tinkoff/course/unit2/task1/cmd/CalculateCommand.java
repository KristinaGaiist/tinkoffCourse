package unit2.task1.cmd;

import java.util.Optional;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.OfficeSuppliesCostCalculator;

public class CalculateCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "calculate";
	}

	@Override
	public String getDescription() {
		return "Для расчета стоимости канцтоваров у определенного сотрудника введите команду calculate arg1, где "
			+ "arg1 - id сотрудника.";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		if (arguments.length != 1) {
			System.out.println(Messages.ILLEGAL_ARGUMENTS + Messages.TRY_AGAIN);
			return;
		}
		int employeeId;
		try {
			employeeId = Integer.parseInt(arguments[0]);
		} catch (NumberFormatException e) {
			System.out.println(Messages.ILLEGAL_EMPLOYEE_ID + Messages.TRY_AGAIN);
			return;
		}
		Optional<Employee> employee = office.getEmployeeById(employeeId);
		if (employee.isEmpty()) {
			System.out.println(String.format(Messages.EMPLOYEE_NOT_FOUND, employeeId) + Messages.TRY_AGAIN);
			return;
		}
		OfficeSuppliesCostCalculator calculator = new OfficeSuppliesCostCalculator();
		System.out.println(String.format(Messages.COST_INFO, employeeId, calculator.calculate(employee.get())));
	}
}
