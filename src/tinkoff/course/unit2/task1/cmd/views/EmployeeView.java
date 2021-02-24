package unit2.task1.cmd.views;

import unit2.task1.application.EmployeeService;
import unit2.task1.cmd.ParseHelper;
import unit2.task1.exception.IllegalArgumentsSizeException;

public class EmployeeView {

    private final EmployeeService employeeService;

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void addEmployee(String[] arguments) {
        if (arguments.length != 2) throw new IllegalArgumentsSizeException();
        employeeService.add(arguments[0], arguments[1]);
    }

    public void getAll() {
        employeeService.getAll()
                .forEach(employee -> System.out.println(employee.getFirstName() + " " + employee.getLastName()));
    }

    public void fireEmployee(String[] arguments) {
        if (arguments.length != 1) throw new IllegalArgumentsSizeException();
        int id = ParseHelper.parseStringToInteger(arguments[0]);
        employeeService.fireEmployee(id);
    }
}
