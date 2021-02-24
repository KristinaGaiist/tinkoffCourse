package unit3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.application.EmployeeService;
import unit2.task1.application.contracts.EmployeeDto;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.exception.EmployeeNotFoundException;
import unit3.CommonTests;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest extends CommonTests {

    @InjectMocks
    private EmployeeService service;

    @Test
    public void getAllEmployeesTest() {
        mockDataStorage();

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
        mockDataStorage(office);

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
        Employee employee = createEmployeeWithRuler();
        mockDataStorage(createOfficeAndAddEmployees(employee));

        service.fireEmployee(employee.getId());
        assertTrue(employee.isFired());
    }

    @Test
    public void employeeIdNotFoundTest() {
        int wrongId = 100;
        mockDataStorage();

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                                                           () -> service.fireEmployee(wrongId));
        assertEquals(String.format("Сотрудник с id = %d не найден.", wrongId), exception.getMessage());
    }
}
