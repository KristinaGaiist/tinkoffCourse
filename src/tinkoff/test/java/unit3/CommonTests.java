package unit3;

import static org.mockito.Mockito.when;

import org.mockito.Mock;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.WorkPlace;
import unit2.task1.domain.task2.Ruler;
import unit2.task1.persistence.IDataStorage;

public class CommonTests {

    @Mock
    protected static IDataStorage dataStorage;

    protected void mockDataStorage(Office office) {
        when(dataStorage.getOffice()).thenReturn(office);
    }

    protected void mockDataStorage() {
        when(dataStorage.getOffice()).thenReturn(new Office());
    }

    protected Employee createEmployeeWithRuler() {
        Employee employee = new Employee("Кристина", "Киринюк");
        WorkPlace workPlace = employee.getWorkPlace();
        workPlace.add(new Ruler(44.56, 20));
        return employee;
    }

    protected Office createOfficeAndAddEmployees(Employee... employees) {
        Office office = new Office();
        for (Employee employee : employees) {
            office.addEmployee(employee);
        }
        return office;
    }
}
