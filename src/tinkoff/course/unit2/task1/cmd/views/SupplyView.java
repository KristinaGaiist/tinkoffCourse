package unit2.task1.cmd.views;

import unit2.task1.application.SupplyService;
import unit2.task1.cmd.ParseHelper;
import unit2.task1.exception.IllegalArgumentsSizeException;

public class SupplyView {

    private final SupplyService supplyService;

    public SupplyView(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    public void addSupply(String[] arguments) {
        if (arguments.length != 3) throw new IllegalArgumentsSizeException();

        double cost = ParseHelper.parseStringToDouble(arguments[2]);
        int id = ParseHelper.parseStringToInteger(arguments[0]);

        supplyService.add(id, arguments[1], cost);
    }

    public void getAll(String[] arguments) {
        if (arguments.length != 1) throw new IllegalArgumentsSizeException();
        int id = ParseHelper.parseStringToInteger(arguments[0]);
        supplyService.getAll(id).forEach(supply -> System.out.println(supply.getName() + " " + supply.getCost()));
    }
}
