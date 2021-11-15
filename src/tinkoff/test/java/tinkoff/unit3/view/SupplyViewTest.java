package tinkoff.unit3.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.SupplyService;
import unit2.task1.cmd.views.SupplyView;
import unit2.task1.exception.IllegalArgumentsSizeException;

@ExtendWith(MockitoExtension.class)
public class SupplyViewTest {

    @Mock
    private SupplyService service;
    @InjectMocks
    private SupplyView view;

    @Test
    public void getAllSuppliesTest() {

        view.getAll(new String[]{String.valueOf(0)});
        verify(service).getAll(0);
    }

    @Test
    public void addSupplyTest() {
        view.addSupply(new String[]{String.valueOf(0), "линейка", String.valueOf(50.5)});
        view.addSupply(new String[]{String.valueOf(1), "ручка", String.valueOf(30.5)});

        verify(service).add(0, "линейка", 50.5);
        verify(service).add(1, "ручка", 30.5);
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
