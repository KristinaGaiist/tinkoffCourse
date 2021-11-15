package tinkoff.unit3.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unit2.task1.application.CalculateSuppliesCostService;
import unit2.task1.cmd.views.CalculateSuppliesCostView;
import unit2.task1.exception.IllegalArgumentsSizeException;

@ExtendWith(MockitoExtension.class)
public class CalculateSuppliesCostViewTest {

    @Mock
    private CalculateSuppliesCostService service;
    @InjectMocks
    private CalculateSuppliesCostView view;

    @Test
    public void calculateTest() {
        view.calculateCost(new String[]{String.valueOf(0)});
        verify(service).calculate(0);
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
