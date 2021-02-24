package unit3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.SupplyService;
import unit2.task1.application.contracts.SupplyDto;
import unit2.task1.domain.Employee;
import unit2.task1.domain.Office;
import unit2.task1.domain.WorkPlace;
import unit2.task1.domain.task2.NoteBook;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.domain.task2.PageType;
import unit2.task1.domain.task2.Pen;
import unit2.task1.domain.task2.PenColor;
import unit2.task1.domain.task2.Ruler;
import unit2.task1.exception.EmployeeNotFoundException;
import unit2.task1.persistence.IDataStorage;
import unit3.CreateEntityHelper;

@ExtendWith(MockitoExtension.class)
public class SupplyServiceTest {

    @Mock
    private IDataStorage dataStorage;
    @InjectMocks
    private SupplyService service;

    @Test
    public void getAllSuppliesTest() {
        Employee employee1 = new Employee("Кристина", "Киринюк");
        WorkPlace workPlace1 = employee1.getWorkPlace();
        workPlace1.add(new Pen(30.5, PenColor.RED));
        workPlace1.add(new Ruler(50.5, 50));

        Employee employee2 = new Employee("Диана", "Шагдонова");
        WorkPlace workPlace2 = employee2.getWorkPlace();
        workPlace2.add(new NoteBook(245.7, 130, PageType.LINE));

        when(dataStorage.getOffice()).thenReturn(CreateEntityHelper.createOfficeAndAddEmployees(employee1, employee2));

        assertThat(service.getAll(employee1.getId()))
            .hasSize(5)
            .extracting(SupplyDto::getName)
            .containsExactlyInAnyOrder("линейка", "ручка", "линейка", "ручка", "блокнот");

        assertThat(service.getAll(employee1.getId()))
            .hasSize(5)
            .extracting(SupplyDto::getCost)
            .containsExactlyInAnyOrder(50.5, 30.5, 33.12, 53.12, 144.1);

        assertThat(service.getAll(employee2.getId()))
            .hasSize(4)
            .extracting(SupplyDto::getName)
            .containsExactlyInAnyOrder("блокнот", "линейка", "ручка", "блокнот");

        assertThat(service.getAll(employee2.getId()))
            .hasSize(4)
            .extracting(SupplyDto::getCost)
            .containsExactlyInAnyOrder(245.7, 33.12, 53.12, 144.1);
    }

    @Test
    public void addSupplyTest() {
        Employee employee = new Employee("Кристина", "Киринюк");
        Office office = CreateEntityHelper.createOfficeAndAddEmployees(employee);
        when(dataStorage.getOffice()).thenReturn(office);

        service.add(employee.getId(), "линейка", 50.5);
        service.add(employee.getId(), "ручка", 30.5);

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
    public void employeeIdNotFoundTest() {
        int wrongId = 100;
        when(dataStorage.getOffice()).thenReturn(new Office());

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                                                           () -> service.add(wrongId, "линейка", 44.5));
        assertEquals(String.format("Сотрудник с id = %d не найден.", wrongId), exception.getMessage());
    }

    @Test
    public void officeSupplierNotFoundTest() {
        Employee employee = CreateEntityHelper.createEmployeeWithRuler();

        when(dataStorage.getOffice()).thenReturn(CreateEntityHelper.createOfficeAndAddEmployees(employee));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                          () -> service.add(employee.getId(), "нож", 44.5));
        assertEquals("Канцелярского товара нож не существует", exception.getMessage());
    }
}
