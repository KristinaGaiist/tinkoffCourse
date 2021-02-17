package unit2.task1.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.OfficeSuppliesCostCalculator;

public class Main {

	public static void main(String... args) throws IOException {
		AddEmployeeCommand addEmployeeCommand = new AddEmployeeCommand();
		AddSupplyCommand addSupplyCommand = new AddSupplyCommand();
		CalculateCommand calculateCommand = new CalculateCommand();
		EmployeesCommand employeesCommand = new EmployeesCommand();
		ExitCommand exitCommand = new ExitCommand();
		SuppliesCommand suppliesCommand = new SuppliesCommand();
		FairEmployeeCommand fairEmployeeCommand = new FairEmployeeCommand();

		CommandExecutor commandExecutor = new CommandExecutor();
		commandExecutor.add(addEmployeeCommand);
		commandExecutor.add(addSupplyCommand);
		commandExecutor.add(calculateCommand);
		commandExecutor.add(employeesCommand);
		commandExecutor.add(exitCommand);
		commandExecutor.add(suppliesCommand);
		commandExecutor.add(fairEmployeeCommand);

		Office office = new Office();
		System.out.println(Messages.INFO_COMMAND);

		while (true) {
			InputStream inputStream = System.in;
			Reader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			System.out.println(Messages.INSERT_COMMAND);
			String command = bufferedReader.readLine();
			String[] arguments = command.split(" ");

			commandExecutor.execute(office, arguments);
		}
	}
}
