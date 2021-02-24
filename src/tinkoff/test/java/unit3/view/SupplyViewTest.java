package unit3.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import unit2.task1.cmd.views.SupplyView;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.WorkPlace;
import unit2.task1.domain.task2.NoteBook;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.PageType;
import unit2.task1.domain.task2.Pen;
import unit2.task1.domain.task2.PenColor;
import unit2.task1.domain.task2.Ruler;
import unit2.task1.exception.IllegalArgumentsSizeException;
import unit3.CommonTests;

@RunWith(MockitoJUnitRunner.class)
public class SupplyViewTest extends CommonTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @InjectMocks
    private SupplyView view;

    @Test
    public void getAllSuppliesTest() {
        Employee employee1 = new Employee("Кристина", "Киринюк");
        WorkPlace workPlace1 = employee1.getWorkPlace();
        workPlace1.add(new Pen(30.5, PenColor.RED));
        workPlace1.add(new Ruler(50.5, 50));

        Employee employee2 = new Employee("Диана", "Шагдонова");
        WorkPlace workPlace2 = employee2.getWorkPlace();
        workPlace2.add(new NoteBook(245.7, 130, PageType.LINE));

        mockDataStorage(createOfficeAndAddEmployees(employee1, employee2));

        view.getAll(new String[]{String.valueOf(employee1.getId())});
        String log1 = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("ручка 33.12 линейка 53.12 блокнот 144.1 ручка 30.5 линейка 50.5")
            .isEqualTo(log1.trim());

        view.getAll(new String[]{String.valueOf(employee2.getId())});
        String log2 = systemOutRule.getLog().replaceAll("\r\n", " ");

        assertThat("ручка 33.12 линейка 53.12 блокнот 144.1 ручка 30.5 линейка 50.5 ручка 33.12 линейка 53.12 "
                       + "блокнот 144.1 блокнот 245.7")
            .isEqualTo(log2.trim());
    }

    @Test
    public void addSupplyTest() {
        Employee employee = new Employee("Кристина", "Киринюк");
        Office office = createOfficeAndAddEmployees(employee);

        mockDataStorage(office);
        view.addSupply(new String[]{String.valueOf(employee.getId()), "линейка", String.valueOf(50.5)});
        view.addSupply(new String[]{String.valueOf(employee.getId()), "ручка", String.valueOf(30.5)});

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
    public void wrongArgumentsSizeAddSupplyTest() {
        IllegalArgumentsSizeException exception = assertThrows(IllegalArgumentsSizeException.class,
                                                               () -> view.addSupply(new String[]{"0"}));
        assertEquals("Неверные аргументы.", exception.getMessage());
    }

    @Test
    public void wrongArgumentsSizeSuppliesTest() {
        IllegalArgumentsSizeException exception = assertThrows(IllegalArgumentsSizeException.class,
                                                               () -> view.addSupply(new String[]{"0", "6"}));
        assertEquals("Неверные аргументы.", exception.getMessage());
    }

    @Test
    public void wrongArgumentTypeIdAddSupplyTest() {
        String wrongArg = "arg";
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                                                       () -> view.addSupply(new String[]{wrongArg, "5.5", "5.4"}));
        assertEquals(String.format("Аргумент %s не может быть конвентирован в число.", wrongArg),
                     exception.getMessage());
    }

    @Test
    public void wrongArgumentTypeCostAddSupplyTest() {
        String wrongArg = "arg";
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                                                       () -> view.addSupply(new String[]{"0", "f", wrongArg}));
        assertEquals(String.format("Аргумент %s не может быть конвентирован в число с плавающей точкой.", wrongArg),
                     exception.getMessage());
    }

    @Test
    public void wrongArgumentTypeIdGetAllSuppliesTest() {
        String wrongArg = "arg";
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                                                       () -> view.getAll(new String[]{wrongArg}));
        assertEquals(String.format("Аргумент %s не может быть конвентирован в число.", wrongArg),
                     exception.getMessage());
    }
}
