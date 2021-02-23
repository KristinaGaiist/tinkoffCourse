package unit2.task1.cmd;

import unit2.task1.cmd.views.*;
import unit2.task1.domain.Office;
import unit2.task1.exception.CommandNotFoundException;
import unit2.task1.persistence.IDataStorage;

import java.util.Arrays;

public final class ShellCommandExecutor {

    public final IDataStorage dataStorage;

    public ShellCommandExecutor(IDataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void execute(String[] arguments) {
        String commandName = arguments[0];
        String[] commandArguments = Arrays.stream(arguments).skip(1).toArray(String[]::new);

        switch (commandName) {
            case "help" -> {
                HelpView helpView = new HelpView();
                helpView.printHelpInfo();
            }
            case "addEmployee" -> getEmployeeView().addEmployee(commandArguments);
            case "employees" -> getEmployeeView().getAll();
            case "fireEmployee" -> getEmployeeView().fireEmployee(commandArguments);
            case "addSupply" -> getSupplyView().addSupply(commandArguments);
            case "supplies" -> getSupplyView().getAll(commandArguments);
            case "calculate" -> {
                CalculateSuppliesCostView calculateSuppliesCostView = new CalculateSuppliesCostView(dataStorage);
                calculateSuppliesCostView.calculateCost(commandArguments);
            }
            case "exit" -> {
                ExitView exitView = new ExitView();
                exitView.exit();
            }
            default -> throw new CommandNotFoundException(commandName);
        }
    }

    private SupplyView getSupplyView() {
        return new SupplyView(dataStorage);
    }

    private EmployeeView getEmployeeView() {
        return new EmployeeView(dataStorage);
    }
}
