package unit2.task1.cmd;

import java.util.Optional;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.OfficeSuppliesCostCalculator;

public class EmployeesCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "employees";
	}

	@Override
	public String getDescription() {
		return "Для отображения списка всех сотрудников введите команду employees.";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		for (Employee employee : office.getEmployees()) {
			System.out.println(employee);
		}
	}
}
