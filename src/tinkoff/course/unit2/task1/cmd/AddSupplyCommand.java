package unit2.task1.cmd;

import java.util.Optional;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.OfficeSupplyCreator;

public class AddSupplyCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "addSupply";
	}

	@Override
	public String getDescription() {
		return "Для добавления канцелярского товара введите команду addSupply arg1 arg2 arg3, где arg1 - id "
			+ "сотрудника, arg2 - название канцтовара, arg3 -"
			+ " цена канцтовара.";
	}

	@Override
	public void handle(Office office, String[] arguments) {
		double cost;
		int id;
		if (arguments.length != 3) {
			System.out.println(Messages.ILLEGAL_ARGUMENTS + Messages.TRY_AGAIN);
			return;
		}
		try {
			cost = Double.parseDouble(arguments[2]);
		} catch (NumberFormatException e) {
			System.out.println(Messages.ILLEGAL_COST + Messages.TRY_AGAIN);
			return;
		}
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
		employee.get().getWorkPlace().add(OfficeSupplyCreator.create(arguments[1], cost));
	}
}
