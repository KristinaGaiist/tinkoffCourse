package unit3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.cmd.ShellCommandExecutor;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.WorkPlace;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.Pen;
import unit2.task1.domain.task2.PenColor;
import unit2.task1.domain.task2.Ruler;
import unit2.task1.exception.CommandNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ShellCommandExecutorTest extends CommonTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @InjectMocks
    private ShellCommandExecutor executor;

    @Test
    public void helpCommandTest() {
        executor.execute(new String[]{"help"});
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("addEmployee arg1 arg2, где arg1 - имя сотрудника,  arg2 - фамилия сотрудника: " +
                       "команда добавления нового сотрудника.\nemployees: команда отображения списка всех сотрудников"
                       + ".\nfireEmployee arg1, где arg1 - id сотрудника: команда увольнения сотрудника.\n" +
                       "addSupply arg1 arg2 arg3, где arg1 - id сотрудника, arg2 - название канцтовара, " +
                       "arg3 - цена канцтовара: команда добавления нового канцелярского товара.\nsupplies arg1, " +
                       "где arg1 - id сотрудника: команда для отображения всех канцелярских товаров у определенного "
                       + "сотрудника\ncalculate arg1, где arg1 - id сотрудника: команда для расчета стоимости всех "
                       + "концелярских "
                       + "товаров у определенного сотрудника.\nexit: команда выхода.")
            .isEqualTo(log.trim());
    }

    @Test
    public void addEmployeeCommandTest() {
        Office office = new Office();
        mockDataStorage(office);

        executor.execute(new String[]{"addEmployee", "Кристина", "Киринюк"});

        assertThat(office.getEmployees())
            .hasSize(1)
            .extracting(Employee::getFirstName)
            .containsExactlyInAnyOrder("Кристина");

        assertThat(office.getEmployees())
            .extracting(Employee::getLastName)
            .containsExactlyInAnyOrder("Киринюк");
    }

    @Test
    public void employeesCommandTest() {
        mockDataStorage(createOfficeAndAddEmployees(new Employee("Кристина", "Киринюк"),
                                                    new Employee("Диана", "Шагдонова"),
                                                    new Employee("Оля", "Петросян")));

        executor.execute(new String[]{"employees"});
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("Кристина Киринюк Диана Шагдонова Оля Петросян").isEqualTo(log.trim());
    }

    @Test
    public void fireEmployeeCommandTest() {
        Employee employee = createEmployeeWithRuler();
        Office office = new Office();
        office.addEmployee(employee);
        mockDataStorage(office);

        executor.execute(new String[]{"fireEmployee", String.valueOf(employee.getId())});

        assertTrue(employee.isFired());
    }

    @Test
    public void addSupplyCommandTest() {
        Employee employee = new Employee("Кристина", "Киринюк");
        Office office = new Office();
        office.addEmployee(employee);

        mockDataStorage(office);

        executor.execute(new String[]{"addSupply", String.valueOf(employee.getId()), "линейка", String.valueOf(50.5)});
        executor.execute(new String[]{"addSupply", String.valueOf(employee.getId()), "ручка", String.valueOf(30.5)});

        List<OfficeSupply> officeSupplies = StreamSupport
            .stream(office.getEmployeeById(employee.getId()).getWorkPlace().getOfficeSupplies().spliterator(), false)
            .collect(Collectors.toList());

        assertThat(officeSupplies)
            .hasSize(5)
            .extracting(OfficeSupply::getName)
            .containsExactlyInAnyOrder("линейка", "ручка", "блокнот", "линейка", "ручка");
        assertThat(officeSupplies)
            .hasSize(5)
            .extracting(OfficeSupply::getCost)
            .containsExactlyInAnyOrder(50.5, 30.5, 33.12, 53.12, 144.1);
    }

    @Test
    public void suppliesCommandTest() {
        Employee employee = new Employee("Диана", "Киринюк");
        WorkPlace workPlace = employee.getWorkPlace();
        workPlace.add(new Pen(30.5, PenColor.RED));
        workPlace.add(new Ruler(50.5, 50));

        Office office = new Office();
        office.addEmployee(employee);
        office.addEmployee(createEmployeeWithRuler());

        mockDataStorage(office);

        executor.execute(new String[]{"supplies", String.valueOf(employee.getId())});
        String log1 = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("ручка 33.12 линейка 53.12 блокнот 144.1 ручка 30.5 линейка 50.5")
            .isEqualTo(log1.trim());
    }

    @Test
    public void calculateCommandTest() {
        Employee employee = createEmployeeWithRuler();
        Office office = new Office();
        office.addEmployee(employee);

        mockDataStorage(office);

        executor.execute(new String[]{"calculate", String.valueOf(employee.getId())});
        String log = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertEquals(String.format("Стоимост канцтоваров у сотрудника %d равна 274,90", employee.getId()), log
            .trim());
    }

    @Test
    public void exitCommandTest() {
        exit.expectSystemExitWithStatus(0);
        executor.execute(new String[]{"exit"});
    }

    @Test
    public void errorCommandTest() {
        String wrongCommand = "wrongCommand";
        CommandNotFoundException exception = assertThrows(CommandNotFoundException.class,
                                                          () -> executor.execute(new String[]{wrongCommand}));
        assertEquals(String.format("Команда %s не найдена", wrongCommand), exception.getMessage());
    }
}
