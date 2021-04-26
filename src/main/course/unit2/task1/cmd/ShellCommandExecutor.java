package unit2.task1.cmd;

import java.util.Arrays;
import unit2.task1.cmd.views.CalculateSuppliesCostView;
import unit2.task1.cmd.views.EmployeeView;
import unit2.task1.cmd.views.ExitView;
import unit2.task1.cmd.views.HelpView;
import unit2.task1.cmd.views.SupplyView;
import unit2.task1.exception.CommandNotFoundException;

public final class ShellCommandExecutor {

    private final CalculateSuppliesCostView calculateSuppliesCostView;
    private final EmployeeView employeeView;
    private final ExitView exitView;
    private final HelpView helpView;
    private final SupplyView supplyView;

    public ShellCommandExecutor(CalculateSuppliesCostView calculateSuppliesCostView,
                                EmployeeView employeeView, ExitView exitView, HelpView helpView,
                                SupplyView supplyView) {
        this.calculateSuppliesCostView = calculateSuppliesCostView;
        this.employeeView = employeeView;
        this.exitView = exitView;
        this.helpView = helpView;
        this.supplyView = supplyView;
    }

    public void execute(String[] arguments) {
        String commandName = arguments[0];
        String[] commandArguments = Arrays.stream(arguments).skip(1).toArray(String[]::new);

        switch (commandName) {
            case "help" -> helpView.printHelpInfo();
            case "addEmployee" -> employeeView.addEmployee(commandArguments);
            case "employees" -> employeeView.getAll();
            case "fireEmployee" -> employeeView.fireEmployee(commandArguments);
            case "addSupply" -> supplyView.addSupply(commandArguments);
            case "supplies" -> supplyView.getAll(commandArguments);
            case "calculate" -> calculateSuppliesCostView.calculateCost(commandArguments);
            case "exit" -> exitView.exit();
            default -> throw new CommandNotFoundException(commandName);
        }
    }
}
