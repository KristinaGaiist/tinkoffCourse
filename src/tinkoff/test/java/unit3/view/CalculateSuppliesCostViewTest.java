package unit3.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.cmd.views.CalculateSuppliesCostView;
import unit2.task1.domain.Employee;
import unit2.task1.exception.IllegalArgumentsSizeException;
import unit3.CommonTests;

@RunWith(MockitoJUnitRunner.class)
public class CalculateSuppliesCostViewTest extends CommonTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @InjectMocks
    private CalculateSuppliesCostView view;

    @Test
    public void calculateTest() {
        Employee employee = createEmployeeWithRuler();
        mockDataStorage(createOfficeAndAddEmployees(employee));

        view.calculateCost(new String[]{String.valueOf(employee.getId())});
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat(String.format("Стоимост канцтоваров у сотрудника %d равна 274,90", employee.getId()))
            .isEqualTo(log.trim());
    }

    @Test
    public void wrongArgumentsSizeTest() {
        IllegalArgumentsSizeException exception = assertThrows(IllegalArgumentsSizeException.class,
                                                               () -> view.calculateCost(new String[]{"0", "6", "arg"}));
        assertEquals("Неверные аргументы.", exception.getMessage());
    }

    @Test
    public void wrongArgumentTypeTest() {
        String wrongArg = "arg";
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                                                       () -> view.calculateCost(new String[]{wrongArg}));
        assertEquals(String.format("Аргумент %s не может быть конвентирован в число.", wrongArg),
                     exception.getMessage());
    }
}
