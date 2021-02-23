package unit2.task1.cmd.views;

import unit2.task1.application.SupplyService;
import unit2.task1.cmd.ParseHelper;
import unit2.task1.domain.Employee;
import unit2.task1.domain.task2.OfficeSupply;
import unit2.task1.exception.EmployeeNotFoundException;
import unit2.task1.exception.IllegalArgumentsSizeException;
import unit2.task1.persistence.IDataStorage;

import java.util.Optional;


public class SupplyView {

    private final SupplyService supplyService;

    public SupplyView(IDataStorage storage) {
        this.supplyService = new SupplyService(storage);
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
