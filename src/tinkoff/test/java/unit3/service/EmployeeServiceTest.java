package unit3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.EmployeeService;
import unit2.task1.application.contracts.EmployeeDto;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.exception.EmployeeNotFoundException;
import unit2.task1.persistence.IDataStorage;
import unit3.CreateEntityHelper;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private IDataStorage dataStorage;
    @InjectMocks
    private EmployeeService service;

    @Test
    public void getAllEmployeesTest() {
        when(dataStorage.getOffice()).thenReturn(new Office());

        service.add("Кристина", "Киринюк");
        service.add("Диана", "Шагдонова");
        service.add("Оля", "Петросян");

        assertThat(service.getAll())
            .hasSize(3)
            .extracting(EmployeeDto::getFirstName)
            .containsExactlyInAnyOrder("Кристина", "Диана", "Оля");

        assertThat(service.getAll())
            .extracting(EmployeeDto::getLastName)
            .containsExactlyInAnyOrder("Киринюк", "Шагдонова", "Петросян");
    }

    @Test
    public void addEmployeeTest() {
        Office office = new Office();
        when(dataStorage.getOffice()).thenReturn(office);

        service.add("Кристина", "Киринюк");

        assertThat(office.getEmployees())
            .hasSize(1)
            .extracting(Employee::getFirstName)
            .containsExactlyInAnyOrder("Кристина");

        assertThat(office.getEmployees())
            .extracting(Employee::getLastName)
            .containsExactlyInAnyOrder("Киринюк");
    }

    @Test
    public void fireEmployeeTest() {
        Employee employee = CreateEntityHelper.createEmployeeWithRuler();
        when(dataStorage.getOffice()).thenReturn(CreateEntityHelper.createOfficeAndAddEmployees(employee));

        service.fireEmployee(employee.getId());
        assertTrue(employee.isFired());
    }

    @Test
    public void employeeIdNotFoundTest() {
        int wrongId = 100;
        when(dataStorage.getOffice()).thenReturn(new Office());

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                                                           () -> service.fireEmployee(wrongId));
        assertEquals(String.format("Сотрудник с id = %d не найден.", wrongId), exception.getMessage());
    }
}
