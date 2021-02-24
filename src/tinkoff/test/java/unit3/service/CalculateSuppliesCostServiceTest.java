package unit3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.application.CalculateSuppliesCostService;
import unit2.task1.domain.Employee;
import unit2.task1.exception.EmployeeNotFoundException;
import unit3.CommonTests;

@RunWith(MockitoJUnitRunner.class)
public class CalculateSuppliesCostServiceTest extends CommonTests {

    @InjectMocks
    private CalculateSuppliesCostService service;

    @Test
    public void calculateTest() {
        Employee employee = createEmployeeWithRuler();

        mockDataStorage(createOfficeAndAddEmployees(employee));
        assertThat(service.calculate(employee.getId())).isEqualTo(274.9);
    }

    @Test
    public void employeeIdNotFoundTest() {
        int wrongId = 100;
        mockDataStorage();

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                                                           () -> service.calculate(wrongId));
        assertEquals(String.format("Сотрудник с id = %d не найден.", wrongId), exception.getMessage());
    }
}
