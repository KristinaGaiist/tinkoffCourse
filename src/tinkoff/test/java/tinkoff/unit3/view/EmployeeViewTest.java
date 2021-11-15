package tinkoff.unit3.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.EmployeeService;
import unit2.task1.application.contracts.EmployeeDto;
import unit2.task1.cmd.views.EmployeeView;
import unit2.task1.exception.IllegalArgumentsSizeException;

@ExtendWith(MockitoExtension.class)
public class EmployeeViewTest {

    @Mock
    private EmployeeService service;
    @InjectMocks
    private EmployeeView view;

    @Test
    public void getAllEmployeesTest() {
        when(service.getAll()).thenReturn(Arrays.asList(new EmployeeDto("Кристина", "Киринюк"),
                                                        new EmployeeDto("Диана", "Шагдонова"),
                                                        new EmployeeDto("Оля", "Петросян")));
        view.getAll();
        verify(service).getAll();
    }

    @Test
    public void addEmployeeTest() {
        view.addEmployee(new String[]{"Кристина", "Киринюк"});
        verify(service).add("Кристина", "Киринюк");
    }

    @Test
    public void fireEmployeeTest() {
        view.fireEmployee(new String[]{String.valueOf(0)});
        verify(service).fireEmployee(0);
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
