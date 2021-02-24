package unit2.task1.cmd.views;

import unit2.task1.application.CalculateSuppliesCostService;
import unit2.task1.cmd.Messages;
import unit2.task1.cmd.ParseHelper;
import unit2.task1.exception.IllegalArgumentsSizeException;

public class CalculateSuppliesCostView {

    private final CalculateSuppliesCostService calculateSuppliesCostService;

    public CalculateSuppliesCostView(CalculateSuppliesCostService calculateSuppliesCostService) {
        this.calculateSuppliesCostService = calculateSuppliesCostService;
    }

    public void calculateCost(String[] arguments) {
        if (arguments.length != 1) throw new IllegalArgumentsSizeException();
        int id = ParseHelper.parseStringToInteger(arguments[0]);

        double cost = calculateSuppliesCostService.calculate(id);
        System.out.println(String.format(Messages.COST_INFO, id, cost));
    }
}
