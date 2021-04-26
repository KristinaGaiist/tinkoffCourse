package unit2.task1.application;

import unit2.task1.domain.Office;
import unit2.task1.domain.OfficeSuppliesCostCalculator;
import unit2.task1.persistence.IDataStorage;

public class CalculateSuppliesCostService {

    private final IDataStorage storage;

    public CalculateSuppliesCostService(IDataStorage storage) {
        this.storage = storage;
    }

    public double calculate(int id) {
        Office office = this.storage.getOffice();
        OfficeSuppliesCostCalculator calculator = new OfficeSuppliesCostCalculator();

        return calculator.calculate(office.getEmployeeById(id));
    }
}
