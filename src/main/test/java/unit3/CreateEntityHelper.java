package unit3;

import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.WorkPlace;
import unit2.task1.domain.task2.Ruler;

public class CreateEntityHelper {

    public static Employee createEmployeeWithRuler() {
        Employee employee = new Employee("Кристина", "Киринюк");
        WorkPlace workPlace = employee.getWorkPlace();
        workPlace.add(new Ruler(44.56, 20));

        return employee;
    }

    public static Office createOfficeAndAddEmployees(Employee... employees) {
        Office office = new Office();
        for (Employee employee : employees) {
            office.addEmployee(employee);
        }
        return office;
    }
}
