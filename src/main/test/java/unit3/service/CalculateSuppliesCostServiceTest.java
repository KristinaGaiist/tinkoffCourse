package unit3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.CalculateSuppliesCostService;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.exception.EmployeeNotFoundException;
import unit2.task1.persistence.IDataStorage;
import unit3.CreateEntityHelper;

@ExtendWith(MockitoExtension.class)
public class CalculateSuppliesCostServiceTest {

    @Mock
    private IDataStorage dataStorage;
    @InjectMocks
    private CalculateSuppliesCostService service;

    @Test
    public void calculateTest() {
        Employee employee = CreateEntityHelper.createEmployeeWithRuler();

        when(dataStorage.getOffice()).thenReturn(CreateEntityHelper.createOfficeAndAddEmployees(employee));
        assertThat(service.calculate(employee.getId())).isEqualTo(274.9);
    }

    @Test
    public void employeeIdNotFoundTest() {
        int wrongId = 100;
        when(dataStorage.getOffice()).thenReturn(new Office());

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                                                           () -> service.calculate(wrongId));
        assertEquals(String.format("Сотрудник с id = %d не найден.", wrongId), exception.getMessage());
    }
}
