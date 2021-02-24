package unit3.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.application.EmployeeService;
import unit2.task1.cmd.views.EmployeeView;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.exception.IllegalArgumentsSizeException;
import unit3.CommonTests;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeViewTest extends CommonTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @InjectMocks
    private EmployeeService service;
    @InjectMocks
    private EmployeeView view;

    @Test
    public void getAllEmployeesTest() {
        mockDataStorage();

        service.add("Кристина", "Киринюк");
        service.add("Диана", "Шагдонова");
        service.add("Оля", "Петросян");

        view.getAll();
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("Кристина Киринюк Диана Шагдонова Оля Петросян").isEqualTo(log.trim());
    }

    @Test
    public void addEmployeeTest() {
        Office office = new Office();
        mockDataStorage(office);

        view.addEmployee(new String[]{"Кристина", "Киринюк"});

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
        view.fireEmployee(new String[]{String.valueOf(employee.getId())});

        assertTrue(employee.isFired());
    }

    @Test
    public void wrongArgumentsSizeAddEmployeeTest() {
        IllegalArgumentsSizeException exception = assertThrows(IllegalArgumentsSizeException.class,
                                                               () -> view.addEmployee(new String[]{"0", "6", "arg"}));
        assertEquals("Неверные аргументы.", exception.getMessage());
    }

    @Test
    public void wrongArgumentsSizeFireEmployeeTest() {
        IllegalArgumentsSizeException exception = assertThrows(IllegalArgumentsSizeException.class,
                                                               () -> view.fireEmployee(new String[]{"0", "6", "arg"}));
        assertEquals("Неверные аргументы.", exception.getMessage());
    }

    @Test
    public void wrongArgumentTypeFireEmployeeTest() {
        String wrongArg = "arg";
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                                                       () -> view.fireEmployee(new String[]{wrongArg}));
        assertEquals(String.format("Аргумент %s не может быть конвентирован в число.", wrongArg),
                     exception.getMessage());
    }
}
