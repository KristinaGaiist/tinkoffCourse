package tinkoff.unit3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.cmd.ShellCommandExecutor;
import unit2.task1.cmd.views.CalculateSuppliesCostView;
import unit2.task1.cmd.views.EmployeeView;
import unit2.task1.cmd.views.ExitView;
import unit2.task1.cmd.views.HelpView;
import unit2.task1.cmd.views.SupplyView;
import unit2.task1.exception.CommandNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ShellCommandExecutorTest {

    @Mock
    private CalculateSuppliesCostView calculateSuppliesCostView;
    @Mock
    private EmployeeView employeeView;
    @Mock
    private ExitView exitView;
    @Mock
    private HelpView helpView;
    @Mock
    private SupplyView supplyView;
    @InjectMocks
    private ShellCommandExecutor executor;

    @Test
    public void helpCommandTest() {
        executor.execute(new String[]{"help"});
        verify(helpView).printHelpInfo();
    }

    @Test
    public void addEmployeeCommandTest() {
        executor.execute(new String[]{"addEmployee", "Кристина", "Киринюк"});
        verify(employeeView).addEmployee(new String[]{"Кристина", "Киринюк"});
    }

    @Test
    public void employeesCommandTest() {
        executor.execute(new String[]{"employees"});
        verify(employeeView).getAll();
    }

    @Test
    public void fireEmployeeCommandTest() {
        executor.execute(new String[]{"fireEmployee", "10"});
        verify(employeeView).fireEmployee(new String[]{"10"});
    }

    @Test
    public void addSupplyCommandTest() {
        executor.execute(new String[]{"addSupply", "0", "линейка", "50.5"});
        executor.execute(new String[]{"addSupply", "12", "ручка", "30.5"});
        verify(supplyView).addSupply(new String[]{"0", "линейка", "50.5"});
        verify(supplyView).addSupply(new String[]{"12", "ручка", "30.5"});
    }

    @Test
    public void suppliesCommandTest() {
        executor.execute(new String[]{"supplies", "122"});
        verify(supplyView).getAll(new String[]{"122"});
    }

    @Test
    public void calculateCommandTest() {
        executor.execute(new String[]{"calculate", "13"});
        verify(calculateSuppliesCostView).calculateCost(new String[]{"13"});
    }

    @Test
    public void exitCommandTest() {
        executor.execute(new String[]{"exit"});
        verify(exitView).exit();
    }

    @Test
    public void errorCommandTest() {
        String wrongCommand = "wrongCommand";
        CommandNotFoundException exception = assertThrows(CommandNotFoundException.class,
                                                          () -> executor.execute(new String[]{wrongCommand}));
        assertEquals(String.format("Команда %s не найдена", wrongCommand), exception.getMessage());
    }
}
